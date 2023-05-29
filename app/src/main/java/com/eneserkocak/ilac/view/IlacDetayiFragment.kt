package com.eneserkocak.ilac.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.eneserkocak.ilac.R
import com.eneserkocak.ilac.databinding.FragmentIlacDetayiBinding
import com.eneserkocak.ilac.databinding.FragmentIlacListesiBinding
import com.eneserkocak.ilac.model.Ilac

import com.google.firebase.FirebaseApp
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage


class IlacDetayiFragment : BaseFragment<FragmentIlacDetayiBinding>(R.layout.fragment_ilac_detayi) {



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        observeLiveData()

    }

    fun observeLiveData() {

        viewModel.secilenIlac.observe(viewLifecycleOwner) { ilac ->

            ilac?.let {
                binding.ilacIsmi.text = it.ilacAdi
                binding.ilacBarkod.text = it.ilacBarkod
                binding.ilacEtkenMadde.text = it.ilacEtkenMadde
                binding.ilacUreticiFirma.text = it.ilacUreticiFirma
                binding.ilacReceteTuru.text = it.ilacReceteTuru
                binding.ilacEndikasyon.text = it.ilacEndikasyon


              /*   val storageRef = FirebaseStorage.getInstance().reference.child("${it.ilacGorsel}")
                storageRef.downloadUrl.addOnSuccessListener {
                    Glide.with(requireContext())
                        .load(it)
                        .into(binding.ilacImage)
                }
                    .addOnFailureListener { e->
                        println(e)

                    }*/

                  }


                }
            }
        }









