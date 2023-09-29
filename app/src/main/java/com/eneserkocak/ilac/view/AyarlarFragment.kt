package com.eneserkocak.ilac.view

import android.os.Bundle
import android.view.View
import com.eneserkocak.ilac.R
import com.eneserkocak.ilac.databinding.FragmentAyarlarBinding
import com.eneserkocak.ilac.model.MIAD_UYARI_SURESI
import com.eneserkocak.ilac.util.AppUtil
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds


class AyarlarFragment : BaseFragment<FragmentAyarlarBinding>(R.layout.fragment_ayarlar) {

    lateinit var mAdView : AdView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //BANNER ADMOB KODLARI
        MobileAds.initialize(requireContext()) {}
        //Banner Ad ID -> ca-app-pub-5511459156486174/6330313161   PLAY STORE YAYINLARKEN XML de BUNA ÇEVİR
        //Banner Test ID -> ca-app-pub-3940256099942544/6300978111
        mAdView = view.findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)



       val sharedPreferences =  AppUtil.getSharedPreferences(requireContext())
        val miadUyariSuresi = sharedPreferences.getInt(MIAD_UYARI_SURESI,0)
        binding.miadUyariEditText.setText(miadUyariSuresi.toString())


        binding.kaydetButon.setOnClickListener {
            binding.miadUyariEditText.text.toString().toIntOrNull()?.let {
                sharedPreferences.edit().putInt(MIAD_UYARI_SURESI,it).apply()
            }

        }
    }

}