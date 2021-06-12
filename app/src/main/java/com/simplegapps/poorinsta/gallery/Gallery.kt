package com.simplegapps.poorinsta.gallery

data class Gallery(val images: List<Image>)

data class Image(
    val id: String,
    val title: String?,
    val url: String,
    val likes: Int,
    val datetime: Long,
    val author: String?,
    val isAlbum: Boolean,
    val imagesAlbumCount: Int
) {
    val authorAvatar: String by lazy {
        "https://imgur.com/user/${author}/avatar"
    }
}

data class Album(
    val id: String,
    val title: String,
    val images: List<AlbumImage>
)

data class AlbumImage(
    val id: String,
    val link: String
)