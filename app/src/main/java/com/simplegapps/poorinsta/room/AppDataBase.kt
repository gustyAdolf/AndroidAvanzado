package com.simplegapps.poorinsta.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [RoomImage::class], version = 2)
@TypeConverters(value = [AppConventers::class])
abstract class AppDataBase : RoomDatabase() {

    abstract fun imageDao(): ImageDAO
}