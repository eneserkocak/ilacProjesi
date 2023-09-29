package com.eneserkocak.ilac.view3

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.eneserkocak.ilac.R
import com.eneserkocak.ilac.adapter.PlaceAdapter
import com.eneserkocak.ilac.databinding.FragmentMapsListeBinding
import com.eneserkocak.ilac.db_maps.PlaceDatabase
import com.eneserkocak.ilac.model.Place
import com.eneserkocak.ilac.view.BaseFragment

const val MAPS_LISTE = "MapsListe"
const val ADRES_EKLE = "AdresEkle"
class MapsListeFragment : BaseFragment<FragmentMapsListeBinding>(R.layout.fragment_maps_liste) {

    var placeList= listOf<Place>()

    val adapter = PlaceAdapter(){
        viewModel.selectedPlace.value= it
        val action = MapsListeFragmentDirections.actionMapsListeFragmentToMapsFragment(MAPS_LISTE)
        findNavController().navigate(action)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       val db = Room.databaseBuilder(requireContext(), PlaceDatabase::class.java, "Places").allowMainThreadQueries().build()
        val placeDao =db.placeDao()

        placeList=placeDao.getAll()



        binding.mapsListRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.mapsListRecycler.adapter = adapter
        adapter.placeListGuncelle(placeList)

        binding.mapsListeToolbar.adresEkle.setOnClickListener {
            val action = MapsListeFragmentDirections.actionMapsListeFragmentToMapsFragment(ADRES_EKLE)
            findNavController().navigate(action)

        }

    }

}