package com.eneserkocak.ilac.view

import android.os.Bundle
import android.view.View
import com.eneserkocak.ilac.R
import com.eneserkocak.ilac.databinding.FragmentSecimBinding
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds


class SecimFragment : BaseFragment<FragmentSecimBinding>(R.layout.fragment_secim) {


    lateinit var mAdView : AdView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //BANNER ADMOB KODLARI
        MobileAds.initialize(requireContext()) {}
        //Banner Ad ID -> ca-app-pub-5511459156486174/5734367041   PLAY STORE YAYINLARKEN XML de BUNA ÇEVİR
        //Banner Test ID -> ca-app-pub-3940256099942544/6300978111
        mAdView = view.findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)



        binding.ilacKutuphanem.setOnClickListener {
            navigate(R.id.ilacListesiFragment)
        }

        binding.eczaDolabim.setOnClickListener {
            navigate(R.id.tumIlaclarFragment)
        }





    }

  }
