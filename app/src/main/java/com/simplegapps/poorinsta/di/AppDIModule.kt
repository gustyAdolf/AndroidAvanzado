package com.simplegapps.poorinsta.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

class AppDIModule(private val application: Application) : DIBaseModule("AppDIModule") {
    override val builder: DI.Builder.() -> Unit = {

        bind<Application>() with singleton { application }

        bind<SharedPreferences>() with singleton {
            instance<Application>().getSharedPreferences("poorinsta", Context.MODE_PRIVATE)
        }

    }

}