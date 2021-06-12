package com.simplegapps.poorinsta.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "IMAGES")
data class RoomImage(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "url") val url: String,
    @ColumnInfo(name = "likes") val likes: Int,
    @ColumnInfo(name = "datetime") val datetime: Long,
    @ColumnInfo(name = "author") val author: String?,
    @ColumnInfo(name = "type") val imageType: ImageType,
    @ColumnInfo(name = "is_album") val isAlbum: Boolean,
    @ColumnInfo(name = "images_count") val imagesAlbumCount: Int
) {
    enum class ImageType {
        HOT, TOP, MY_IMAGES
    }
}