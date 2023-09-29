package com.eneserkocak.ilac.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.eneserkocak.ilac.model.AlarmSaat
import com.eneserkocak.ilac.model.Ilac

@Dao
    interface IlacDao {
        @Query("SELECT * FROM ilaclar")
        fun getAll(): LiveData<List<Ilac>>

        @Query("SELECT * FROM ilaclar WHERE skt < :bugunTarih+:miadUyariSure AND skt >:bugunTarih " )
        fun miadiYaklasanlariGetir(bugunTarih : Long,miadUyariSure : Long): List<Ilac>


        @Query("SELECT * FROM ilaclar WHERE skt<:bugunTarih  ")
        fun miadiGecenleriGetir(bugunTarih : Long): List<Ilac>




        @Insert
        fun insertAll(vararg ilaclar: Ilac)

        @Delete
        fun delete(ilac: Ilac):Int

        @Insert
        fun insertAlarm(vararg alarmSaat: AlarmSaat)

    @Query("SELECT * FROM alarmSaatleri")
    fun getAlarmSaatleri(): LiveData<List<AlarmSaat>>


    }

