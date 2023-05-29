package com.eneserkocak.ilac.view

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels

import com.eneserkocak.ilac.R
import com.eneserkocak.ilac.model.ILACLAR
import com.eneserkocak.ilac.model.Ilac
import com.eneserkocak.ilac.model.MIAD_UYARI_SURESI
import com.eneserkocak.ilac.util.Util
import com.eneserkocak.ilac.viewmodel.IlacViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MainActivity : AppCompatActivity() {
    val viewModel : IlacViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        val sharedPref = Util.getSharedPreferences(this)
        val miadUyariSuresi = sharedPref.getInt(MIAD_UYARI_SURESI,0)
        if (miadUyariSuresi==0)
            sharedPref.edit().putInt(MIAD_UYARI_SURESI,3).apply()


    viewModel.setData(this)

    }

}