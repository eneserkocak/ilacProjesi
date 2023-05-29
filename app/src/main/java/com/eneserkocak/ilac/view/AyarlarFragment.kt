package com.eneserkocak.ilac.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.eneserkocak.ilac.R
import com.eneserkocak.ilac.databinding.FragmentAyarlarBinding
import com.eneserkocak.ilac.model.MIAD_UYARI_SURESI
import com.eneserkocak.ilac.util.Util


class AyarlarFragment : BaseFragment<FragmentAyarlarBinding>(R.layout.fragment_ayarlar) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       val sharedPreferences =  Util.getSharedPreferences(requireContext())
        val miadUyariSuresi = sharedPreferences.getInt(MIAD_UYARI_SURESI,0)
        binding.miadUyariEditText.setText(miadUyariSuresi.toString())


        binding.kaydetButon.setOnClickListener {
            binding.miadUyariEditText.text.toString().toIntOrNull()?.let {
                sharedPreferences.edit().putInt(MIAD_UYARI_SURESI,it).apply()
            }

        }
    }

}