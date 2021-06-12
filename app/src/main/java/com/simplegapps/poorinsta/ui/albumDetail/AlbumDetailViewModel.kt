package com.simplegapps.poorinsta.ui.albumDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.simplegapps.poorinsta.gallery.Album
import com.simplegapps.poorinsta.gallery.AlbumImage
import com.simplegapps.poorinsta.gallery.GalleryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AlbumDetailViewModel(
    private val galleryRepository: GalleryRepository
) : ViewModel() {
    private val stateFlow: MutableStateFlow<AlbumState> = MutableStateFlow(AlbumState.empty())
    val state: StateFlow<AlbumState>
        get() = stateFlow

    private var requestJob: Job? = null

    fun loadAlbumImages(id: String) {
        fetchAlbumImages { galleryRepository.getAlbum(id) }
    }

    private fun fetchAlbumImages(source: suspend () -> Album) {
        requestJob?.cancel()
        requestJob = viewModelScope.launch(Dispatchers.IO) {
            stateFlow.value = AlbumState.empty()
            val images = source().images
            stateFlow.value = AlbumState.from(images)
        }
    }


    data class AlbumState(val images: List<AlbumImage>, val loading: Boolean) {
        companion object {
            fun from(images: List<AlbumImage>): AlbumState {
                return AlbumState(
                    images = images,
                    loading = false
                )
            }
            fun empty() = AlbumState(emptyList(), true)
        }
    }
}