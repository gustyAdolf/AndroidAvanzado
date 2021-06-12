package com.simplegapps.poorinsta.di

import com.simplegapps.poorinsta.network.AuthInterceptor
import com.simplegapps.poorinsta.network.ImgurApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object NetworkDIModule : DIBaseModule("NetworkDIModule") {

    override val builder: DI.Builder.() -> Unit = {
        bind<OkHttpClient>() with singleton {
            OkHttpClient().newBuilder()
                .addInterceptor(AuthInterceptor(instance()))
                .build()
        }

        bind<Moshi>() with singleton {
            Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        }

        bind<Retrofit>() with singleton {
            Retrofit.Builder()
                .baseUrl("https://api.imgur.com/3/")
                .client(instance())
                .addConverterFactory(MoshiConverterFactory.create(instance()))
                .build()
        }

        bind<ImgurApi>() with singleton {
            instance<Retrofit>().create(ImgurApi::class.java)
        }
    }
}