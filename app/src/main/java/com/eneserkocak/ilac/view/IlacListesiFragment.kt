package com.eneserkocak.ilac.view

import android.os.Binder
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.eneserkocak.ilac.R
import com.eneserkocak.ilac.adapter.IlacRecyclerAdapter
import com.eneserkocak.ilac.databinding.FragmentIlacListesiBinding
import com.eneserkocak.ilac.model.Ilac
import java.util.*
import kotlin.collections.ArrayList


class IlacListesiFragment : BaseFragment<FragmentIlacListesiBinding>(R.layout.fragment_ilac_listesi) {

    private lateinit var searchView: SearchView
    var searchList = mutableListOf<Ilac>()
    var ilacListesi= mutableListOf<Ilac>()

    val adapter = IlacRecyclerAdapter(){
        viewModel.secilenIlac.value = it
        findNavController().navigate(R.id.ilacDetayiFragment)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
                super.onViewCreated(view, savedInstanceState)




        searchView = binding.search
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

        /*viewModel.ilacHataMesaji.observe(viewLifecycleOwner, Observer { hata->
            hata?.let {
                if(it){
                    binding.ilacHataMesaji.visibility=View.VISIBLE
                    binding.ilacListRecycler.visibility=View.GONE
                }else{
                    binding.ilacHataMesaji.visibility=View.GONE
                }
            }
        })*/

       /* viewModel.ilacYukleniyor.observe(viewLifecycleOwner, Observer { ilacYukleniyor->
            ilacYukleniyor?.let {
                if (it){
                    binding.ilacListRecycler.visibility=View.GONE
                    binding.ilacHataMesaji.visibility=View.GONE
                    binding.search.visibility=View.GONE
                    binding.ilacYukleniyor.visibility=View.VISIBLE

                }else{
                    binding.ilacYukleniyor.visibility=View.GONE
                    binding.search.visibility=View.VISIBLE
                }
            }
        })*/
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
                                if (it.ilacAdi.uppercase(Locale.getDefault()).contains(searchText.uppercase(Locale.getDefault()))){
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






