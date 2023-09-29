package com.eneserkocak.ilac.view

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.eneserkocak.ilac.R
import com.eneserkocak.ilac.databinding.FragmentIlacDetayiBinding
import com.eneserkocak.ilac.util.AppUtil
import com.eneserkocak.ilac.viewmodel.IlacViewModel
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds


class IlacDetayiFragment : DialogFragment(){
    lateinit var binding: FragmentIlacDetayiBinding
    val viewModel:IlacViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentIlacDetayiBinding.inflate(inflater,container,false)
        return binding.root
    }

    lateinit var mAdView : AdView


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        observeLiveData()

        //BANNER ADMOB KODLARI
        MobileAds.initialize(requireContext()) {}
        //Banner Ad ID -> ca-app-pub-5511459156486174/8141038532    PLAY STORE YAYINLARKEN XML de BUNA ÇEVİR
        //Banner Test ID -> ca-app-pub-3940256099942544/6300978111
        mAdView = view.findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)


    }
    // Full Screen Diaog Kod:
    override fun getTheme(): Int {
        return R.style.myFullScreenDialogStyle

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
                binding.ilacEndikasyon.movementMethod = ScrollingMovementMethod()

                //Görsel indirme fonksiyonu AppUtil de..Oradaki fonk kul. burda görseli çekiyoruz
                AppUtil.gorselIndir(it,requireContext(),binding.ilacImage)

                //Görsel indirme Fonk nunu AppUtil e aldık (aşağıdaki kodlar)
                /*fun gorselIndir(ilac: Ilac, context: Context, imageView: ImageView){
                    val dosyaIsmi= "${ilac.id}.jpg"
                    //İLAÇ GÖRSELLERİNİ FİREBASE DEN ÇEK
                    val storageRef = FirebaseStorage.getInstance().reference.child(dosyaIsmi)
                    storageRef.downloadUrl.addOnSuccessListener {
                        Glide.with(context)
                            .load(it)
                            .into(imageView)
                    }
                        .addOnFailureListener { e->
                            println(e)

                        }*/

                  }


                }
            }
        }










