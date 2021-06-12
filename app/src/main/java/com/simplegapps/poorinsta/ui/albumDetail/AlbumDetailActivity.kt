package com.simplegapps.poorinsta.ui.albumDetail

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.simplegapps.poorinsta.databinding.AlbumDetailActivityBinding
import kotlinx.coroutines.flow.collect
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.di
import org.kodein.di.direct
import org.kodein.di.instance

class AlbumDetailActivity : AppCompatActivity(), DIAware {

    override val di: DI by di()
    private lateinit var binding: AlbumDetailActivityBinding
    private lateinit var adapter: AlbumDetailRecyclerAdapter
    private val viewModel: AlbumDetailViewModel by lazy {
        ViewModelProvider(this, direct.instance()).get(AlbumDetailViewModel::class.java)
    }
    var albumId: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AlbumDetailActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        albumId = intent.getStringExtra("albumId")
        configureRecyclerView()
        if (albumId != null) {
            viewModel.loadAlbumImages(albumId!!)
        }
        observeViewModel()
    }

    private fun configureRecyclerView() {
        adapter = AlbumDetailRecyclerAdapter().also {
            binding.recyclerAlbum.adapter = it
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launchWhenStarted {
            viewModel.state.collect {
                adapter.images = it.images
                binding.homeProgressBar.visibility = when (it.loading) {
                    true -> View.VISIBLE
                    false -> View.GONE
                }
            }
        }
    }

}