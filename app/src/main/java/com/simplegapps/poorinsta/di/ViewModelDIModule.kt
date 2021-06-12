package com.simplegapps.poorinsta.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.simplegapps.poorinsta.ui.albumDetail.AlbumDetailViewModel
import com.simplegapps.poorinsta.ui.top.TopViewModel
import org.kodein.di.*
import org.kodein.type.erased

object ViewModelDIModule : DIBaseModule("ViewModelDIModule") {

    override val builder: DI.Builder.() -> Unit = {
        bind<ViewModelProvider.Factory>() with singleton {
            DIViewModelFactory(di)
        }

        bind<TopViewModel>() with provider { TopViewModel(instance(), instance()) }

        bind<AlbumDetailViewModel>() with provider { AlbumDetailViewModel(instance()) }
    }

    class DIViewModelFactory(private val di: DI) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return di.direct.Instance(erased(modelClass))
        }
    }
}