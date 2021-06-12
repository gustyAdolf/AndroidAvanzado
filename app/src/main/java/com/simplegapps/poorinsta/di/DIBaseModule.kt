package com.simplegapps.poorinsta.di

import org.kodein.di.DI

abstract class DIBaseModule(val name: String) {
    fun create() = DI.Module(name = name, allowSilentOverride = false, init = builder)

    abstract val builder: DI.Builder.() -> Unit
}