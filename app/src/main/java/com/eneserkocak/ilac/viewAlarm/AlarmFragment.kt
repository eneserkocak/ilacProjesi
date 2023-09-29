package com.eneserkocak.ilac.viewAlarm

import android.app.AlarmManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.eneserkocak.ilac.R
import com.eneserkocak.ilac.adapter.AlarmAdapter
import com.eneserkocak.ilac.databinding.DialogAlarmBinding
import com.eneserkocak.ilac.db.IlacDao
import com.eneserkocak.ilac.model.Ilac
import com.eneserkocak.ilac.model.Saat
import com.eneserkocak.ilac.viewmodel.IlacViewModel
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat.CLOCK_24H
import com.kozan.alarm.Alarm
import com.kozan.alarm.AlarmUtil
import java.text.SimpleDateFormat
import java.util.*


class AlarmFragment : DialogFragment(){

    lateinit var binding:DialogAlarmBinding
    var mContext: Context? = null
    lateinit var dao: IlacDao
    lateinit var secilenIlac: Ilac
    val  alarmSaatList  = mutableListOf<Saat>()

    val viewModel: IlacViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogAlarmBinding.inflate(inflater,container,false)
        return binding.root
    }

    lateinit var mAdView : AdView


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mContext = requireContext()
       // setPermissions()

        viewModel.secilenIlac.observe(viewLifecycleOwner){
            it?.let {
                secilenIlac = it
            }
        }


        //XML içinde view Model ı kullanmak için yazıyoruz:
        binding.viewModel=viewModel

        binding.lifecycleOwner = viewLifecycleOwner

        val adapter = AlarmAdapter(){}
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
        val sdf = SimpleDateFormat("E,dd/MM/yyyy kk:mm:ss")
        AlarmUtil.getAlarms(requireContext())?.observe(viewLifecycleOwner) {
            it?.let {
               val list =  it.filter { it.id==secilenIlac.id.toInt() }
                    list.forEach {
                        val dateTime = sdf.format(it.time)
                   println(dateTime)
               }
                    adapter.updateItems(list)
                }
        }

        binding.alarmEkleBtn.setOnClickListener {
           showTimerPickerFragment(requireContext())
        }



        /*alarmSaatleri.observe(viewLifecycleOwner){
            it?.let {

                val list = it.find { it.ilacId == viewModel.secilenIlac.value?.id }
                list?.alarmList?.let { it1 -> adapter.updateItems(it1) }

            }

        }*/

        //BANNER ADMOB KODLARI
        MobileAds.initialize(requireContext()) {}
        //Banner Ad ID -> ca-app-pub-5511459156486174/7401788423    PLAY STORE YAYINLARKEN XML de BUNA ÇEVİR
        //Banner Test ID -> ca-app-pub-3940256099942544/6300978111
        mAdView = view.findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)



    }

    override fun getTheme(): Int {
        return R.style.alarmFragmentFullScreenDialogStyle

    }



   /* private fun setPermissions() {
        val alarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val isPermissionGranted =  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            alarmManager.canScheduleExactAlarms()
        } else {
            true
        }
        if (!isPermissionGranted) startActivity(Intent(ACTION_REQUEST_SCHEDULE_EXACT_ALARM))

        ActivityCompat.requestPermissions(
            requireActivity(), arrayOf(android.Manifest.permission.POST_NOTIFICATIONS), 1)
    }*/


    fun showTimerPickerFragment(context: Context) {
        val materialTimePicker: MaterialTimePicker = MaterialTimePicker.Builder()
            .setTimeFormat(CLOCK_24H)
            .build()

        materialTimePicker.show(parentFragmentManager, "MainActivity")

        materialTimePicker.addOnPositiveButtonClickListener {
            val saat = "${materialTimePicker.hour}:${materialTimePicker.minute}"
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.HOUR_OF_DAY, materialTimePicker.hour)
            calendar.set(Calendar.MINUTE, materialTimePicker.minute)
            calendar.set(Calendar.SECOND, 0)
            //startAlarm(calendar)
            val alarm= Alarm(secilenIlac.id.toInt(),calendar.timeInMillis,AlarmManager.INTERVAL_DAY,"${binding.ilacAdiTextV.text.toString()  } ZAMANI")
            AlarmUtil.setAlarm(context,alarm)


        }
    }

   /* private fun startAlarm(calendar: Calendar) {
        val alarmManager = mContext?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        //Bu kod: ilaç adını Notification da göstermek için, intent le burdan Notification Utils e verecek
        val intent = Intent(requireContext(), AlarmReceiver::class.java).apply {
            putExtra(ILAC_ADI,viewModel.secilenIlac.value?.ilacAdi)
        }
        val pendingIntent = PendingIntent.getBroadcast(
            requireContext(),
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )
        //alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
    }*/



    }
