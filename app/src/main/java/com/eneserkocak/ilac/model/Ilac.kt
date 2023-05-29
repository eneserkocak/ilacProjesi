package com.eneserkocak.ilac.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.Timestamp
import com.google.gson.annotations.SerializedName
import java.util.*


@Entity(tableName = "ilaclar")
data class Ilac (
    var ilacId: String="",
    var ilacAdi: String="",
    var ilacBarkod: String="",
    var ilacEtkenMadde: String="",
    var ilacUreticiFirma:String="",
    var ilacReceteTuru: String="",
    var ilacGorsel: String="",
    var ilacEndikasyon: String="",
    var skt : Date? = null,
    @PrimaryKey(autoGenerate = true)
    val roomId : Int = 0
    ) {
}