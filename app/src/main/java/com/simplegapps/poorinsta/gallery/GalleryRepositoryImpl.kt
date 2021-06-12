package com.simplegapps.poorinsta.gallery

import com.simplegapps.poorinsta.network.ImgurApi
import com.simplegapps.poorinsta.network.NetworkAlbum
import com.simplegapps.poorinsta.network.NetworkGallery
import com.simplegapps.poorinsta.room.ImageDAO
import com.simplegapps.poorinsta.room.RoomImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class GalleryRepositoryImpl(
    private val imgurApi: ImgurApi,
    private val imageDAO: ImageDAO
) : GalleryRepository {

    override suspend fun getHotGallery(): Gallery =
        withContext(Dispatchers.IO) {
            try {
                imgurApi.getHotGallery().toDomain().also { gallery ->
                    imageDAO.insertImages(gallery.toRoom(RoomImage.ImageType.HOT))
                }
            } catch (e: Exception) {
                Timber.e(e)
                imageDAO.getImages(RoomImage.ImageType.HOT).toDomain()
            }
        }

    override suspend fun getAlbum(id: String): Album =
        withContext(Dispatchers.IO) {
            imgurApi.getAlbum(id).toDomain()
        }


    override suspend fun getTopGallery(): Gallery =
        withContext(Dispatchers.IO) {
            try {
                imgurApi.getTopGallery().toDomain().also { gallery ->
                    imageDAO.insertImages(gallery.toRoom(RoomImage.ImageType.TOP))
                }
            } catch (e: Exception) {
                Timber.e(e)
                imageDAO.getImages(RoomImage.ImageType.TOP).toDomain()
            }
        }


    override suspend fun getMyGallery(): Gallery =
        withContext(Dispatchers.IO) { imgurApi.getMyGallery().toDomain() }


    private fun NetworkGallery.toDomain(): Gallery {
        val images = data.filter { image ->
            val imageLink = image.images?.first()?.link ?: image.link
            imageLink.contains(".jpg") || imageLink.contains(".png")
        }.mapNotNull { image ->
            val imageLink = image.images?.first()?.link ?: image.link
            Image(
                id = image.id,
                title = image.title,
                url = imageLink,
                likes = image.favorite_count ?: 0,
                datetime = image.datetime,
                author = image.account_url,
                isAlbum = image.is_album ?: false,
                imagesAlbumCount = image.images_count ?: 0
            )
        }
        return Gallery(images)
    }

    private fun NetworkAlbum.toDomain(): Album {
        val images = data.images.filter {
            it.link.contains(".jpg") || it.link.contains(".png")
        }.mapNotNull { image ->
            AlbumImage(
                id = image.id,
                link = image.link
            )
        }
        return Album(id = data.id, title = data.title, images = images)
    }

    private fun List<RoomImage>.toDomain(): Gallery {
        return Gallery(map { roomImage ->
            Image(
                id = roomImage.id,
                title = roomImage.title,
                url = roomImage.url,
                likes = roomImage.likes,
                datetime = roomImage.datetime,
                author = roomImage.author,
                isAlbum = roomImage.isAlbum,
                imagesAlbumCount = roomImage.imagesAlbumCount
            )
        })
    }

    private fun Gallery.toRoom(imageType: RoomImage.ImageType): List<RoomImage> =
        images.map { image ->
            RoomImage(
                id = image.id,
                title = image.title,
                url = image.url,
                likes = image.likes,
                datetime = image.datetime,
                author = image.author,
                imageType = imageType,
                isAlbum = image.isAlbum,
                imagesAlbumCount = image.imagesAlbumCount
            )
        }
}