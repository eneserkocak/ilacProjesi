package com.eneserkocak.ilac.view

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.eneserkocak.ilac.R
import com.eneserkocak.ilac.databinding.ActivityMainBinding
import com.eneserkocak.ilac.model.MIAD_UYARI_SURESI
import com.eneserkocak.ilac.util.AppUtil
import com.eneserkocak.ilac.viewmodel.IlacViewModel
import com.kozan.alarm.AlarmUtil

class MainActivity : AppCompatActivity(), NavController.OnDestinationChangedListener {
    val viewModel : IlacViewModel by viewModels()
    lateinit var binding: ActivityMainBinding
    lateinit var navController : NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        // setContentView(R.layout.activity_main)

        AlarmUtil.PermissionManager.register(this)

        setNavController()

        // adMob APP ID -> ca-app-pub-5511459156486174~9763394391


        val sharedPref = AppUtil.getSharedPreferences(this)
        val miadUyariSuresi = sharedPref.getInt(MIAD_UYARI_SURESI, 0)
        if (miadUyariSuresi == 0)
            sharedPref.edit().putInt(MIAD_UYARI_SURESI, 3).apply()


        viewModel.setData(this)
    }

    fun setNavController(){
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController
        setSupportActionBar(binding.materialToolbar)
        setupActionBarWithNavController(navController) //title
        binding.materialToolbar.setupWithNavController(navController) // up
        navController.addOnDestinationChangedListener(this)
    }
        //ALARM KODLARI:
       /* fun onTimeSet(timePicker: TimePicker?, hour: Int, minute: Int) {

            val calendar = Calendar.getInstance()
            calendar.set(Calendar.HOUR_OF_DAY, hour)
            calendar.set(Calendar.MINUTE, minute)
            calendar.set(Calendar.SECOND, 0)
            startAlarm(calendar)
        }*/


   /* private fun startAlarm(calendar: Calendar) {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0)
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
    }*/

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        binding.materialToolbar.setNavigationIcon(R.drawable.ilac_detay_back)
        if(destination.id == R.id.secimFragment || destination.id==R.id.animasyonFragment) binding.materialToolbar.visibility = View.GONE
        else binding.materialToolbar.visibility = View.VISIBLE

    }

}