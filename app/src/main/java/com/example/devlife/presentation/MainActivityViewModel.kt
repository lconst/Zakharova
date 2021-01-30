package com.example.devlife.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.devlife.model.entities.Post
import com.example.devlife.model.repository.PostsRepository
import kotlinx.coroutines.*

class MainActivityViewModel(private val repository: PostsRepository): ViewModel() {

    private val _post = MutableLiveData<Post>()
    val post: LiveData<Post> get() = _post

    private val coroutineScope = CoroutineScope(Job() + Dispatchers.IO)
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e(TAG, "Coroutine exception, scope active:${coroutineScope.isActive}", throwable)
        coroutineScope
    }

    fun next() {
        coroutineScope.launch(exceptionHandler) {
            _post.postValue(repository.getRandom())
        }
    }

    fun prev() {

    }

    companion object {
        private val TAG = MainActivityViewModel::class.java.simpleName
    }
}