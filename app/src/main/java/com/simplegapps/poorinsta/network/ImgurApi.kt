package com.simplegapps.poorinsta.network

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface ImgurApi {

    @Headers("Authorization: Client-ID 3795c60af5383c1")
    @GET("gallery/hot")
    suspend fun getHotGallery(): NetworkGallery

    @Headers("Authorization: Client-ID 3795c60af5383c1")
    @GET("gallery/top")
    suspend fun getTopGallery(): NetworkGallery

    @Headers("Authorization: Client-ID 3795c60af5383c1")
    @GET("album/{id}")
    suspend fun getAlbum(@Path("id") id: String): NetworkAlbum

    @GET("account/me/images")
    suspend fun getMyGallery(): NetworkGallery

}