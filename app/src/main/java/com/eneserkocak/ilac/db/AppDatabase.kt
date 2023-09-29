package com.eneserkocak.ilac.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.eneserkocak.ilac.model.AlarmSaat
import com.eneserkocak.ilac.model.Ilac

@Database(entities = [Ilac::class,AlarmSaat::class], version = 4)
@TypeConverters(TypeConventer::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun ilacDao(): IlacDao

    companion object {
        var INSTANCE: AppDatabase?=null

        fun getInstance(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        AppDatabase::class.java, "user.db").allowMainThreadQueries().fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }

}