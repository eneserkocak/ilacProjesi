package com.eneserkocak.ilac.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import android.view.animation.Transformation
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eneserkocak.ilac.model.ILACLAR
import com.eneserkocak.ilac.model.Ilac
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.nio.channels.AsynchronousFileChannel.open
import java.util.*

class IlacViewModel:ViewModel() {

    val ilaclar = MutableLiveData<List<Ilac>>()
    val ilacHataMesaji = MutableLiveData<Boolean>()
    val ilacYukleniyor = MutableLiveData<Boolean>()

    val secilenIlac =MutableLiveData<Ilac>()


    val qrValue = MutableLiveData<String>()

    var miadUyariSuresi = 0


    val skt = MutableLiveData<String>()

    val barcode = MutableLiveData<String>()


    fun setData(context : Context) {

        val jsonFileString: String = context.assets.open("ilac.json").bufferedReader().use {
            it.readText()
        }

        val gson = Gson()
        val listPersonType = object : TypeToken<List<Ilac>>() {}.type

        var ilacListesi: List<Ilac> = gson.fromJson(jsonFileString, listPersonType)

       ilaclar.value = ilacListesi

        //Aşağıdaki kodlar ile verileri firebase e yüklüyoruz..Ancak 7500 veri kota aşımı ve kasma dan dolayı verileri firebase
        //e yüklemeyip..YUKARIDA App içine Assets içine kaydediyorum.

        /*  ilacListesi.forEach(){

            val ilacHashMap= hashMapOf<String,Any>()

            ilacHashMap.put( "ilacId", it.ilacId)
            ilacHashMap.put("ilacAdi" , it.ilacAdi)
            ilacHashMap.put ("ilacBarkod" , it.ilacBarkod)
            ilacHashMap.put("ilacEtkenMadde" , it.ilacEtkenMadde)
            ilacHashMap.put ("ilacUreticiFirma" , it.ilacUreticiFirma)
            ilacHashMap.put("ilacReceteTuru" , it.ilacReceteTuru)
            ilacHashMap.put ("ilacGorsel" , it.ilacGorsel)
            ilacHashMap.put ("ilacEndikasyon" , it.ilacEndikasyon)



            println(ilacHashMap)
            FirebaseFirestore.getInstance().collection(ILACLAR).document(it.ilacId.toString()).set(ilacHashMap)
                .addOnSuccessListener {
                    println()
                }
                .addOnFailureListener {
                    println(it)
                }
        }
    }*/


    }

 // Firebase den verileri bu fonk ile çekiyoruz. Bu iptal oldu verileri(json) app e yükledik
 /*  @SuppressLint("SuspiciousIndentation")
    fun verileriGetir(){
                        //ilacYukleniyor.value=true

        val queryRef= FirebaseFirestore.getInstance().collection(ILACLAR).get()
            viewModelScope.launch (Dispatchers.IO){
                try {
                    val ilacListesi= queryRef.await().toObjects(Ilac::class.java)

                    withContext(Dispatchers.Main){
                        ilaclar.value= ilacListesi
                        ilacHataMesaji.value= false
                        ilacYukleniyor.value= false
                }

            }catch (e:java.lang.Exception){
                        ilacHataMesaji.value=true
                        ilacYukleniyor.value=false
                e.printStackTrace()



            }
         }
    }*/


}