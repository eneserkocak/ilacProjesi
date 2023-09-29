package com.eneserkocak.ilac.view

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import androidx.navigation.fragment.findNavController
import com.eneserkocak.ilac.R
import com.eneserkocak.ilac.databinding.FragmentAnimasyonBinding
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback


@Suppress("DEPRECATION")
class AnimasyonFragment : BaseFragment<FragmentAnimasyonBinding>(R.layout.fragment_animasyon) {

    private var mInterstitialAd: InterstitialAd? = null
    private final var TAG = "Animasyon Fragment"

    private val splashScreen =3000

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //adMob App ID -> ca-app-pub-5511459156486174~6067353625

        //Interstitial Test ID -> ca-app-pub-3940256099942544/1033173712
        //Animasyon Fragment Interstitial Ad ID -> ca-app-pub-5511459156486174/7105436265  PLAY STORE YAYINLARKEN BUNA ÇEVİR

        //INTERSTITIAL ADMOB KODLARI
        var adRequest = AdRequest.Builder().build()

        InterstitialAd.load(requireContext(),"ca-app-pub-5511459156486174/7105436265", adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                adError?.toString()?.let { Log.d(TAG, it) }
                mInterstitialAd = null
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                Log.d(TAG, "Ad was loaded.")
                mInterstitialAd = interstitialAd
            }

        })

        //ANİMASYON
            //supportActionBar?.hide()

        val animasyon1 = AnimationUtils.loadAnimation(getContext(), R.anim.animasyon1)
        val animasyon2 = AnimationUtils.loadAnimation(getContext(), R.anim.animasyon2)
        val animasyon3 = AnimationUtils.loadAnimation(getContext(), R.anim.animasyon3)


        val imageView= binding.imageView
        val imageView2= binding.imageView2
        val baslik= binding.baslik
        //val aciklama= binding.aciklama

        imageView.animation= animasyon1
        imageView2.animation=animasyon2
        baslik.animation= animasyon3
        //aciklama.animation= animasyon3

        //SplashScreen Oluştur:

        Handler().postDelayed({

   //Geri Animasyon Fragment a geri dönmeyecek
        findNavController().popBackStack()
         findNavController().navigate(R.id.secimFragment)

            //INTERSTITIAL ADMOD YÜKLENDİYSE FRAGMENT GEÇİŞ TE GÖSTERR->
            if (mInterstitialAd != null) {
                mInterstitialAd?.show(requireActivity())

            }


        },splashScreen.toLong())


    }

}