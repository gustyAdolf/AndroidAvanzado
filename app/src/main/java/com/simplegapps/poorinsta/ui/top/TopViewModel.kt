package com.simplegapps.poorinsta.ui.top

import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.simplegapps.poorinsta.alsoIfTrue
import com.simplegapps.poorinsta.gallery.Gallery
import com.simplegapps.poorinsta.gallery.GalleryRepository
import com.simplegapps.poorinsta.gallery.Image
import com.simplegapps.poorinsta.session.Session
import com.simplegapps.poorinsta.session.SessionRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

class TopViewModel(
    private val galleryRepository: GalleryRepository,
    private val sessionRepository: SessionRepository
) : ViewModel() {

    private val stateFlow: MutableStateFlow<GalleryState> = MutableStateFlow(GalleryState.empty())
    val state: StateFlow<GalleryState>
        get() = stateFlow

    private val sessionFlow: MutableStateFlow<SessionState> = MutableStateFlow(SessionState.empty())
    val session: StateFlow<SessionState>
        get() = sessionFlow


    private val handler = CoroutineExceptionHandler { coroutineContext, throwable ->
        Timber.e(throwable)
        stateFlow.value = GalleryState(emptyList(), true)
    }


    private var requestJob: Job? = null

    fun loadTopGallery() {
        fetchGallery { galleryRepository.getHotGallery() }
    }

    private fun fetchGallery(source: suspend () -> Gallery) {
        requestJob?.cancel()
        requestJob = viewModelScope.launch(Dispatchers.IO) {
            stateFlow.value = GalleryState(emptyList(), true)
            val images = source().images
            stateFlow.value = GalleryState.from(images)
        }
    }


    fun processIntentData(intent: Intent) {
        val url = intent.data.toString()
        "imgram://oauth2.+".toRegex().matches(url).alsoIfTrue {
            val accestoken = "access_token=(\\w+)".toRegex().find(url)!!.groupValues[1]
            "expires_in=(\\w+)".toRegex()
                .find(url)!!.groupValues[1].toLong() + System.currentTimeMillis()
            "token_type=(\\w+)".toRegex().find(url)!!.groupValues[1]
            "refresh_token=(\\w+)".toRegex().find(url)!!.groupValues[1]
            val accountName = "account_username=(\\w+)".toRegex().find(url)!!.groupValues[1]
            "account_id=(\\w+)".toRegex().find(url)!!.groupValues[1]

            Session(accestoken, accountName)
                .also { session ->
                    sessionRepository.saveSession(session)
                }.also { session ->
                    sessionFlow.value = SessionState(true, session.accountName)
                }
        }
    }


    data class GalleryState(val images: List<Image>, val loading: Boolean) {
        companion object {
            fun from(images: List<Image>): GalleryState {
                return GalleryState(
                    images = images.sortedByDescending { it.datetime },
                    loading = false
                )
            }

            fun empty() = GalleryState(emptyList(), true)
        }
    }

    data class SessionState(val hasSession: Boolean, val accountName: String?) {
        companion object {
            fun empty() = SessionState(false, null)
        }
    }
}