package com.eneserkocak.ilac.db

import androidx.room.TypeConverter
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
    }
