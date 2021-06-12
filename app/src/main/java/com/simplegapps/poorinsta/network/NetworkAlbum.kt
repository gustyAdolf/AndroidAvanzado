package com.simplegapps.poorinsta.network

data class NetworkAlbum(
    val data: NetworkAlbumItem,
    val success: Boolean,
    val status: Int
) {
    data class NetworkAlbumItem(
        val id: String,
        val title: String,
        val images: List<NetworkAlbumImage>
    )

    data class NetworkAlbumImage(
        val id: String,
        val title: String?,
        val description: String?,
        val type: String,
        val link: String
    )
}

