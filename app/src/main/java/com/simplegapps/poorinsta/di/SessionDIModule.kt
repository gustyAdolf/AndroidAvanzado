package com.simplegapps.poorinsta.di

import com.simplegapps.poorinsta.session.SessionLocalDataSource
import com.simplegapps.poorinsta.session.SessionMemoryDataSource
import com.simplegapps.poorinsta.session.SessionRepository
import com.simplegapps.poorinsta.session.SessionRepositoryImpl
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

object SessionDIModule : DIBaseModule("SessionDIModule") {

    override val builder: DI.Builder.() -> Unit = {

        bind<SessionLocalDataSource>() with singleton {
            SessionLocalDataSource((instance()))
        }

        bind<SessionMemoryDataSource>() with singleton {
            SessionMemoryDataSource()
        }

        bind<SessionRepository>() with singleton {
            SessionRepositoryImpl(instance(), instance())
        }
    }
}