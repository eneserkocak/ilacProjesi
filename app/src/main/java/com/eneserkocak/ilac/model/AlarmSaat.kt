package com.eneserkocak.ilac.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "alarmSaatleri")
data class AlarmSaat(
    val ilacId :String,
    val ilacAdi : String,
    val alarmList : List<Saat>,
    @PrimaryKey(autoGenerate = true)
    val roomId :Int = 0
)

data class Saat(
    val saat :Int,
    val dakika :Int,
)
