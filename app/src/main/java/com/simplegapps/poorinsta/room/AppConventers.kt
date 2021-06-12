package com.simplegapps.poorinsta.room

import androidx.room.TypeConverter

class AppConventers {

    @TypeConverter
    fun toImageType(value: String) = RoomImage.ImageType.valueOf(value)

    @TypeConverter
    fun fromImageType(imageType: RoomImage.ImageType) = imageType.name
}