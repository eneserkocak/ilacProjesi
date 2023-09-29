package com.eneserkocak.ilac.view2

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.eneserkocak.ilac.R
import com.eneserkocak.ilac.adapter.TumIlaclarAdapter
import com.eneserkocak.ilac.databinding.DialogIlacFaydaBinding
import com.eneserkocak.ilac.databinding.FragmentTumIlaclarBinding
import com.eneserkocak.ilac.db.AppDatabase
import com.eneserkocak.ilac.db.IlacDao
import com.eneserkocak.ilac.model.ClickedButton
import com.eneserkocak.ilac.model.Ilac
import com.eneserkocak.ilac.model.MIAD_UYARI_SURESI
import com.eneserkocak.ilac.util.AppUtil
import com.eneserkocak.ilac.view.BaseFragment
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.kozan.alarm.AlarmUtil
import java.util.*


class TumIlaclarFragment() : BaseFragment<FragmentTumIlaclarBinding>(R.layout.fragment_tum_ilaclar) {

    lateinit var dao: IlacDao
    lateinit var adapter: TumIlaclarAdapter

        private lateinit var searchView: SearchView
    var searchList = mutableListOf<Ilac>()
    lateinit var ilacListesiLD : LiveData<List<Ilac>>
    var ilacListesi = listOf<Ilac>()

    lateinit var mAdView : AdView



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Aşağıda searchView e kadar TOOGLE BUTTON U MANUEL YAPACAĞIZ..

        //Tüm İlaçlar Button
        binding.clickedButton = ClickedButton.TumIlaclar

        dao = AppDatabase.getInstance(requireContext())!!.ilacDao()
        ilacListesiLD = dao.getAll()


        //Miadı geçenler Button
        val miadiGecenler =  dao.miadiGecenleriGetir(System.currentTimeMillis())
        binding.mGecenlerCount.text = miadiGecenler.size.toString()


        //Miadı Yaklaşanlar Button
        //val tarih = SimpleDateFormat("ddMMyy").parse("280927").time
        val sharedPreferences =  AppUtil.getSharedPreferences(requireContext())
        val miadUyariSuresi = sharedPreferences.getInt(MIAD_UYARI_SURESI,0)
        val miadUyariSureLong = miadUyariSuresi*30*24*60*60*1000L

        val miadiYaklasanlar =  dao.miadiYaklasanlariGetir(System.currentTimeMillis(),miadUyariSureLong)
        binding.mYaklasanlarCount.text = miadiYaklasanlar.size.toString()


        searchView = binding.yeniIlacSearch
        searchView.setOnClickListener {
            searchView.isIconified = false
        }


        //Adapter deki row a tıklanınca:
        adapter = TumIlaclarAdapter(viewLifecycleOwner,{ilac->
            AlarmUtil.PermissionManager.checkPermissions(requireActivity() as AppCompatActivity){
                if (!it) {
                  Toast.makeText(context,"Devam etmek için gerekli izinleri sağlamanız gerekmektedir.",Toast.LENGTH_LONG).show()
                    return@checkPermissions
                }
                findNavController().navigate(R.id.alarmFragment)
                viewModel.secilenIlac.value= ilac
            }},
            {
            ilacFaydasiGoster(it)

        }
        )


        binding.tumListeRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.tumListeRecycler.adapter = adapter

        //TumIlaclarFragment ta Tum Ilaclar Button a basmadan Recyler Liste Görünmüyor..
        // İlaç Listesini buton a basmadan da göstermek için aşağıdaki kodlar:
        ilacListesiLD.observe(viewLifecycleOwner){
            it?.let {
                ilacListesi = it
                binding.tumIlaclarCount.text = it.size.toString()
                if (it.isNotEmpty()){
                    binding.placeHolderLayout.visibility = View.GONE
                    binding.tumListeRecycler.visibility = View.VISIBLE

                    adapter.updateItems(it)
                }else{
                    binding.placeHolderLayout.visibility = View.VISIBLE
                    binding.tumListeRecycler.visibility = View.GONE
                }

            }
        }

        yeniListSearch()

        val clickListener : (ClickedButton)->Unit = {
            binding.clickedButton = it
        }

        binding.tumIlaclarButton.setOnClickListener {
            clickListener.invoke(ClickedButton.TumIlaclar)
            adapter.updateItems(ilacListesi)
            //aşağıdaki kod çalışırken her "tüm ilaçlara" basmada ayrı bir fragment açıyordu ve çıkmak için açılan fragment kdar geri tuşuna basıyoduk.
            //navigate(R.id.tumIlaclarFragment)

        }


        binding.miadiYaklasanButton.setOnClickListener {
            clickListener.invoke(ClickedButton.MiadıYaklasanlar)
            adapter.updateItems(miadiYaklasanlar)


        }

        binding.miadiGecenlerButton.setOnClickListener {
            clickListener.invoke(ClickedButton.MiadiGecenler)
            adapter.updateItems(miadiGecenler)

        }

        binding.yeniIlacEkleButton.setOnClickListener {

            //Yeni ilaç ekle ye her gittiğimizde önceki okutulan ilaç bilgileri silinmiyordu.
            //Bu nu engellemek için her gidişte "null" a eşitle
             viewModel.qrsecilenIlac.value = null

            /*
            var yeniIlacUyariGoster = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_ilac_ekle,null)
            val alertDialog =AlertDialog.Builder(requireContext())
            alertDialog.setView(yeniIlacUyariGoster)
            alertDialog.show()

             */

            navigate(R.id.yeniIlacEkleFragment)

        }

        //REKLAM KODLARI ::::::::::::::::::::::::::::::::::::::::::::::::::::
        MobileAds.initialize(requireContext()) {}
        //Banner Ad ID -> ca-app-pub-5511459156486174/9290468679   PLAY STORE YAYINLARKEN XML de BUNA ÇEVİR
        //Banner Test ID -> ca-app-pub-3940256099942544/6300978111
        //BANNER ADMOB KODLARI
        mAdView = view.findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)






    }

    //dialog_ilac_fayda XML de yazacağımız kodları aşagıdaki fonk içine yazacağız.

    private fun ilacFaydasiGoster(it: Ilac) {

        val binding = DialogIlacFaydaBinding.inflate(LayoutInflater.from(requireContext()))
        binding.ilac = it
        val dialog = AlertDialog.Builder(requireContext()).setView(binding.root).show()

       // dialog.setButton("Kapat"){dialog,which->}

        AppUtil.gorselIndir(it,requireContext(),binding.ilacFaydaGorsel)


    }
    //Yeni ilaç eklerken göstereceğimiz Alert Dialog
    /* private fun yeniIlacUyariGoster(it:Ilac){

       val binding= DialogIlacEkleBinding.inflate(LayoutInflater.from(requireContext()))
       binding.ilac =it
       val dialog = AlertDialog.Builder(requireContext()).setView(binding.root).show() } */





    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        inflater.inflate(R.menu.ayarlar_menu,menu)

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        findNavController().navigate(R.id.ayarlarFragment)
        return super.onOptionsItemSelected(item)
    }



    private fun yeniListSearch() {
        searchView.clearFocus()
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                searchList.clear()

                val searchText= newText!!.toLowerCase(Locale.getDefault())

                if (searchText.isNotEmpty()){
                    ilacListesi.forEach{
                        if (it.ilacAdi.uppercase(Locale.getDefault()).contains(searchText.uppercase(Locale.US))){
                            searchList.add(it)
                        }
                    }
                    adapter.updateItems(searchList.toList())

                }else{
                    adapter.updateItems(ilacListesi)
                }
                return true
            }


        })


    }


}