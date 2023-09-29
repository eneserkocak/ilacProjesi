package com.eneserkocak.ilac.view

import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.eneserkocak.ilac.R
import com.eneserkocak.ilac.adapter.IlacRecyclerAdapter
import com.eneserkocak.ilac.databinding.FragmentIlacListesiBinding
import com.eneserkocak.ilac.model.Ilac
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import java.util.*


class IlacListesiFragment : BaseFragment<FragmentIlacListesiBinding>(R.layout.fragment_ilac_listesi) {

    lateinit var mAdView : AdView

    private lateinit var searchView: SearchView
    var searchList = mutableListOf<Ilac>()

    var ilacListesi= mutableListOf<Ilac>()

    val adapter = IlacRecyclerAdapter(){
        viewModel.secilenIlac.value = it
        findNavController().navigate(R.id.ilacDetayiFragment)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
                super.onViewCreated(view, savedInstanceState)


        //BANNER ADMOB KODLARI
        MobileAds.initialize(requireContext()) {}
        //Banner Ad ID -> ca-app-pub-5511459156486174/2341916943   PLAY STORE YAYINLARKEN XML de BUNA ÇEVİR
        //Banner Test ID -> ca-app-pub-3940256099942544/6300978111
        mAdView = view.findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)


        searchView = binding.search
        //search içinde herhangi bir yere tıklayınca search çalışacak:
        searchView.setOnClickListener {
            searchView.isIconified = false
        }

        binding.ilacListRecycler.layoutManager=LinearLayoutManager(requireContext())
        binding.ilacListRecycler.adapter= adapter

       // viewModel.setData(requireContext())

       /* binding.swipeRefreshLayout.setOnRefreshListener {

            binding.ilacYukleniyor.visibility=View.GONE
            binding.ilacHataMesaji.visibility=View.GONE
            binding.ilacListRecycler.visibility=View.GONE
            binding.search.visibility= View.GONE
            viewModel.setData(requireContext())
            binding.swipeRefreshLayout.isRefreshing=false
        }*/

        observeLiveData()


        search()

         }



    fun observeLiveData(){

        viewModel.ilaclar.observe(viewLifecycleOwner, Observer { ilaclar->
            ilaclar?.let {

                ilacListesi = it.toMutableList()

                adapter.ilacListesiniGuncelle(ilaclar)

                binding.ilacListRecycler.visibility= View.VISIBLE

            }
        })


    }


          private fun search() {
               searchView.clearFocus()
               searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                  override fun onQueryTextSubmit(query: String?): Boolean {
                    searchView.clearFocus()
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
                           adapter.ilacListesiniGuncelle(searchList.toList())

                       }else{
                           adapter.ilacListesiniGuncelle(ilacListesi)
                       }
                    return true
            }


        })



    }

}






