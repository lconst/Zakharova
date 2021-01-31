package com.example.devlife.presentation.post

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.devlife.Cache
import com.example.devlife.model.repository.PostsRepository

@Suppress("UNCHECKED_CAST")
class PostViewModelFactory(
    private val cache: Cache,
    private val repository: PostsRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when (modelClass){
        PostViewModel::class.java -> PostViewModel(cache, repository)
        else -> throw IllegalArgumentException("$modelClass is not registered ViewModel")
    } as T
}