package com.example.andersenhw.presentation.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.andersenhw.databinding.ActivityDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {
    private var _binding: ActivityDetailBinding? = null
    private val binding get() = _binding!!
    private val imagesAdapter by lazy { ImagesAdapter() }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: DetailViewModel by viewModels(factoryProducer = { viewModelFactory })
    private val viewModelCat by viewModels<CatFactsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val name = intent.getStringExtra(ITEM_NAME).orEmpty()
        viewModel.loadImages(name)
        setupViews()
        observeViewModel()
        usePagingSource(imagesAdapter)
    }

    private fun usePagingSource(adapter: ImagesAdapter){
        lifecycleScope.launch {
            viewModelCat.pagerNoRemoteMediator.collectLatest {
                adapter.submitData(it)
            }
        }
    }


    private fun setupViews() {
        binding.rvImages.apply {
            adapter = imagesAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun observeViewModel() {
        viewModel.detailStateView.observe(this) {
            if (it.error != null) {
                showError(it.error)
            } else {
                showData(it.images.orEmpty())
            }
        }
    }

    private fun showData(list: List<String>) {
        imagesAdapter.updateData(list)

    }

    private fun showError(error: Int) {
        Timber.d("error: $error")
    }

    companion object {
        const val ITEM_NAME = "ITEM_NAME"
        fun getIntent(context: Context, name: String): Intent {
            return Intent(context, DetailActivity::class.java).apply {
                putExtra(ITEM_NAME, name)
            }
        }
    }
}