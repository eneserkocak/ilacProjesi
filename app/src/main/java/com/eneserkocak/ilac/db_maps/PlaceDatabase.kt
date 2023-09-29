package com.eneserkocak.ilac.db_maps

import androidx.room.Database
import androidx.room.RoomDatabase
import com.eneserkocak.ilac.model.Place

    @Database(entities = arrayOf(Place::class), version = 1)
    abstract class  PlaceDatabase: RoomDatabase(){
         abstract fun placeDao(): PlaceDao
}