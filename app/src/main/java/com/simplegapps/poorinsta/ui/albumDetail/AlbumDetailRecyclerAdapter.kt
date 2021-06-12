package com.simplegapps.poorinsta.ui.albumDetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.simplegapps.poorinsta.databinding.AlbumDetailItemLayoutBinding
import com.simplegapps.poorinsta.gallery.AlbumImage
import com.simplegapps.poorinsta.load
import timber.log.Timber
import kotlin.math.log

class AlbumDetailRecyclerAdapter : RecyclerView.Adapter<AlbumItemViewHolder>() {

    var images: List<AlbumImage> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumItemViewHolder =
        AlbumDetailItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            .run { AlbumItemViewHolder(this) }

    override fun onBindViewHolder(holder: AlbumItemViewHolder, position: Int) {
        holder.bind(image = images[position])
    }

    override fun getItemCount(): Int = images.count()
}


class AlbumItemViewHolder(
    private val binding: AlbumDetailItemLayoutBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(image: AlbumImage) {
        with(binding) {
            albumItemImageView.setImageBitmap(null)
            albumItemImageView.load(image.link)
        }
    }
}