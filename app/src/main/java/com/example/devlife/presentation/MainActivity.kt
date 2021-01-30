package com.example.devlife.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.devlife.PostApp
import com.example.devlife.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainActivityViewModel by viewModels {
        MainActivityViewModelFactory((application as PostApp).networkModule.repository) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.next.setOnClickListener { viewModel.next() }
        binding.prev.setOnClickListener { viewModel.prev() }

        viewModel.post.observe(this) {
            Glide
                .with(this)
                .load(it.gifURL)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.image)
        }
    }
}