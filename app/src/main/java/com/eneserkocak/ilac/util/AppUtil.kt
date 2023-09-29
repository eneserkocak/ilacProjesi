package com.eneserkocak.ilac.util

import android.content.Context
import android.content.SharedPreferences
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.eneserkocak.ilac.model.Ilac
import com.google.firebase.storage.FirebaseStorage

object AppUtil {

    fun ImageView.gorselIndir(url: String?) {

        Glide.with(context).load(url).into(this)
    }



    fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)

    }
    //Firebase Storage dan görsel i burada indiriyoruz..İlaç Detayı ve İlaçFaydaDialog ta AppUtil. diyerek grselleri çekyoruz
    //Aşağıdaki kodlar farklı yerdlerde ayrı ayrı yazılmamış oluyor.

    fun gorselIndir(ilac: Ilac,context: Context,imageView: ImageView){
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

            }
    }

}