package com.eneserkocak.ilac.db

import androidx.room.TypeConverter
import com.eneserkocak.ilac.model.Saat
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

class TypeConventer {


        @TypeConverter
        fun fromLongtoDate(value: Long?): Date? {
            return value?.let { Date(it) }
        }

        @TypeConverter
        fun fromDateToLong(date: Date?): Long? {
            return if (date == null) null else date.getTime()
        }



    @TypeConverter
    fun fromResultList(value: MutableList<Saat>): String {
        val gson = Gson()
        val type = object : TypeToken<MutableList<Saat>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toResultList(value: String): MutableList<Saat> {
        val gson = Gson()
        val type = object : TypeToken<MutableList<Saat>>() {}.type
        return gson.fromJson(value, type)
    }




}
