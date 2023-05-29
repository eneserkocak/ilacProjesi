package com.eneserkocak.ilac.view2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.set
import androidx.navigation.fragment.findNavController
import com.eneserkocak.ilac.R

import com.eneserkocak.ilac.databinding.FragmentYeniIlacEkleBinding
import com.eneserkocak.ilac.db.AppDatabase
import com.eneserkocak.ilac.view.BaseFragment
import com.kozan.mylibrary.utils.apputils.ToastUtil


class yeniIlacEkleFragment() : BaseFragment<FragmentYeniIlacEkleBinding>(R.layout.fragment_yeni_ilac_ekle) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel


        binding.qrCodeButton.setOnClickListener {
            navigate(R.id.qrCodeFragment)
        }

        binding.kaydet.setOnClickListener {

                viewModel.secilenIlac.value?.let { secilenIlac->
                  AppDatabase.getInstance(requireContext())?.ilacDao()?.insertAll(secilenIlac)
                }

            ToastUtil.shortToast(requireContext(),"Yeni ilaç eklendi")
            findNavController().navigate(R.id.tumIlaclarFragment)





            }
        }

}
