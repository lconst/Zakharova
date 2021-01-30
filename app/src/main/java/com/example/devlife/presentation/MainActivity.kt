package com.example.devlife.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.devlife.PostApp
import com.example.devlife.databinding.ActivityMainBinding
import com.example.devlife.model.entities.Post

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainActivityViewModel by viewModels {
        MainActivityViewModelFactory((application as PostApp).networkModule.repository) }

    private val progressLoader by lazy { createProgressLoader() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.next.setOnClickListener { viewModel.newPost() }
        binding.prev.setOnClickListener { viewModel.prev() }

        viewModel.newPost()

        viewModel.post.observe(this) { handleOnReadyPost(it) }
        viewModel.hasPrev.observe(this) {handleOnNavigation(it)}

    }

    private fun handleOnNavigation(hasPrev: Boolean) {
        binding.prev.isVisible = hasPrev
    }

    private fun handleOnReadyPost(it: Post) {
        Glide
            .with(this)
            .load(it.gifURL)
            .placeholder(progressLoader)
            .fitCenter()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .transform(RoundedCorners(roundingRadius))
            .into(binding.image)

        binding.description.text = it.description
    }

    private fun createProgressLoader() = CircularProgressDrawable(this).also {
        it.strokeWidth = 5f
        it.centerRadius = 30f
        it.start()
    }

    companion object {
        private const val roundingRadius = 10
    }
}