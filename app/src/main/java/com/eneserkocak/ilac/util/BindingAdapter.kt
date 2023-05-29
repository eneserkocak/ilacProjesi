package com.eneserkocak.ilac.util

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.Date

object BindingAdapters {

   @JvmStatic
    @BindingAdapter("dateToString")
    fun convertTimestamp(textView: TextView, date : Date?) {
       val sdf = SimpleDateFormat("dd.MM.yyyy")

      date?.let {
          val sktString = sdf.format(it)
          textView.text = sktString
      }

    }
}