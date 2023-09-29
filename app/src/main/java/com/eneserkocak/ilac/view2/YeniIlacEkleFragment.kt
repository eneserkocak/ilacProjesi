package com.eneserkocak.ilac.view2


import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.eneserkocak.ilac.R
import com.eneserkocak.ilac.databinding.FragmentYeniIlacEkleBinding
import com.eneserkocak.ilac.db.AppDatabase
import com.eneserkocak.ilac.view.BaseFragment
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.android.material.datepicker.MaterialDatePicker
import com.kozan.mylibrary.utils.apputils.ToastUtil
import java.text.SimpleDateFormat
import java.util.*


class yeniIlacEkleFragment() : BaseFragment<FragmentYeniIlacEkleBinding>(R.layout.fragment_yeni_ilac_ekle) {

    lateinit var mAdView : AdView



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //binding.ilacinFaydalariText.setMovementMethod(ScrollingMovementMethod())

        //XML içinde ViewModel ı kullanmak için yazıyoruz:
        binding.viewModel = viewModel
       // viewModel.qrsecilenIlac.value = null

        //BANNER ADMOB KODLARI
        MobileAds.initialize(requireContext()) {}
        //Banner Ad ID -> ca-app-pub-5511459156486174/6493964698   PLAY STORE YAYINLARKEN XML de BUNA ÇEVİR
        //Banner Test ID -> ca-app-pub-3940256099942544/6300978111
        mAdView = view.findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)


        binding.qrCodeButton.setOnClickListener {

        //QR CODE barkod tarayıcı ya girmek için kullanıcıdan izni burada ve aşağıda ON CREATE dışında soracağız
            if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                askForCameraPermission()
                //requestCodeCameraPermission
            } else {
                findNavController().navigate(R.id.qrCodeFragment)
            }



            //navigate(R.id.qrCodeFragment)
        }

        binding.kaydet.setOnClickListener {

          val skt = binding.editTextDate.text.toString()
            if (skt.isEmpty()){
                ToastUtil.longToast(requireContext(),"Son kullanma tarihi girilmeden ilaç eklenemez.!                                 Son kullanma tarihi butonuna 2 kere tıklayarak, tarih girebilirsiniz.")
                return@setOnClickListener
            }

            viewModel.qrsecilenIlac.value?.let { secilenIlac->
                AppDatabase.getInstance(requireContext())?.ilacDao()?.insertAll(secilenIlac)
                ToastUtil.shortToast(requireContext(),"Yeni ilaç eklendi")
                findNavController().popBackStack()
                navigate(R.id.tumIlaclarFragment)

            }
        }

    // Barkod okutulursa Tarih elle girilecek...(edit text e datePicker koyacağız)..
        val myFormat= "dd.MM.yyyy"
        val sdf = SimpleDateFormat(myFormat)

        binding.editTextDate.setOnClickListener {
            val datePicker =
                MaterialDatePicker.Builder.datePicker()
                    .setTitleText("SON KULLANIM TARİHİ")
                    .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                    .build()
            datePicker.addOnPositiveButtonClickListener {
                val date = Date(it)
                binding.editTextDate.setText(sdf.format(date))
                viewModel.qrsecilenIlac.value?.skt = date
            }
            datePicker.show(childFragmentManager, "tag")
        }





    }
    //QR BARKOD OKUYUCU İZNİNİ BURADAN ALACAZ..KULLANICI İZİN VERİRSE QR CODE A GİDECEK

    fun askForCameraPermission() {
        ActivityCompat.requestPermissions(requireActivity(),arrayOf(android.Manifest.permission.CAMERA), 1234)
    }

    //override fun
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1234 && grantResults.isNotEmpty()) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                findNavController().navigate(R.id.qrCodeFragment)
            } else {
                Toast.makeText(requireContext(), "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }


}



