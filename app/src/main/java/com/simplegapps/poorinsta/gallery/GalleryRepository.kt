package com.simplegapps.poorinsta.gallery

interface GalleryRepository {

    suspend fun getHotGallery(): Gallery

    suspend fun getTopGallery(): Gallery

    suspend fun getMyGallery(): Gallery

    suspend fun getAlbum(id: String): Album
}