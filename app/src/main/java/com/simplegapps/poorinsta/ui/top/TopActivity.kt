package com.simplegapps.poorinsta.ui.top

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.simplegapps.poorinsta.R
import com.simplegapps.poorinsta.databinding.TopActivityBinding
import kotlinx.coroutines.flow.collect
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.di
import org.kodein.di.direct
import org.kodein.di.instance

class TopActivity : AppCompatActivity(), DIAware {

    override val di: DI by di()
    private lateinit var binding: TopActivityBinding
    private lateinit var adapter: TopRecyclerAdapter
    private val viewModel: TopViewModel by lazy {
        ViewModelProvider(this, direct.instance()).get(TopViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = TopActivityBinding.inflate(layoutInflater).also { setContentView(it.root) }
        configureRecyclerView()
        observeViewModel()
        configureBottomNav()

    }

    private fun configureRecyclerView() {
        adapter = TopRecyclerAdapter().also { binding.galleryRecyclerView.adapter = it }
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

    private fun configureBottomNav() {
        binding.homeBottomNavBar.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.menu_top -> viewModel.loadTopGallery()
            }
            true
        }
        binding.homeBottomNavBar.selectedItemId = R.id.menu_top
    }
}