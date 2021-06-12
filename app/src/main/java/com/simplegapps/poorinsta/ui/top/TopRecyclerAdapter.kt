package com.simplegapps.poorinsta.ui.top

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.simplegapps.poorinsta.databinding.GalleryItemLayoutBinding
import com.simplegapps.poorinsta.gallery.Image
import com.simplegapps.poorinsta.load
import com.simplegapps.poorinsta.ui.albumDetail.AlbumDetailActivity

class TopRecyclerAdapter : RecyclerView.Adapter<GalleryItemViewHolder>() {

    var images: List<Image> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryItemViewHolder =
        GalleryItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            .run { GalleryItemViewHolder(this) }

    override fun onBindViewHolder(holder: GalleryItemViewHolder, position: Int) {
        holder.bind(image = images[position])
    }

    override fun getItemCount(): Int = images.count()
}


class GalleryItemViewHolder(
    private val binding: GalleryItemLayoutBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(image: Image) {
        with(binding) {
            galleryItemTitleText.text = image.title ?: "No title"
            galleryItemAuthorText.text = image.author
            galleryItemImageView.setImageBitmap(null)
            galleryItemAuthorAvatarImage.setImageBitmap(null)
            galleryItemLikesTextView.text = "${image.likes} Likes"

            galleryItemImageView.load(image.url)
            galleryItemAuthorAvatarImage.load(image.authorAvatar) {
                it.circleCrop()
            }

            if (image.isAlbum && image.imagesAlbumCount > 1) {
                moreButton.visibility = View.VISIBLE
            }

            moreButton.setOnClickListener {
                val intent = Intent(itemView.context, AlbumDetailActivity::class.java)
                intent.putExtra("albumId", image.id)
                itemView.context.startActivity(intent)
            }
        }
    }
}