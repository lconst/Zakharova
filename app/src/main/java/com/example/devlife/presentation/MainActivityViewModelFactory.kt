package com.example.devlife.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.devlife.model.repository.PostsRepository

@Suppress("UNCHECKED_CAST")
class MainActivityViewModelFactory(private val repository: PostsRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when (modelClass){
        MainActivityViewModel::class.java -> MainActivityViewModel(repository)
        else -> throw IllegalArgumentException("$modelClass is not registered ViewModel")
    } as T
}