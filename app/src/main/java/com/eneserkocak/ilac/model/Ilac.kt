package com.eneserkocak.ilac.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
@Entity(tableName = "ilaclar")
data class Ilac (
    var id: String="",
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
    ) : Parcelable {
}