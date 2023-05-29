package com.eneserkocak.ilac.util

import android.content.Context
import android.content.SharedPreferences
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

object Util {

    fun ImageView.gorselIndir(url: String?) {

        Glide.with(context).load(url).into(this)
    }

    fun placeHolderYap(context: Context): CircularProgressDrawable {

        return CircularProgressDrawable(context).apply {

            strokeWidth = 8f
            centerRadius = 40f
            start()
        }


    }

    fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)

    }
}