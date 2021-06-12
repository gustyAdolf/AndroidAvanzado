package com.simplegapps.poorinsta.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ImageDAO {

    @Query("SELECT * FROM IMAGES WHERE TYPE = :imageType")
    suspend fun getImages(imageType: RoomImage.ImageType): List<RoomImage>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertImages(imagesList: List<RoomImage>)
}