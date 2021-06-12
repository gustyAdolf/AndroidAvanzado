package com.simplegapps.poorinsta.di

import androidx.room.Room
import com.simplegapps.poorinsta.room.AppDataBase
import com.simplegapps.poorinsta.room.ImageDAO
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

object RoomDIModule : DIBaseModule("RoomDIModule") {

    override val builder: DI.Builder.() -> Unit = {
        bind<AppDataBase>() with singleton {
            Room.databaseBuilder(instance(), AppDataBase::class.java, "simplegapps")
                .build()
        }

        bind<ImageDAO>() with singleton {
            instance<AppDataBase>().imageDao()
        }
    }
}