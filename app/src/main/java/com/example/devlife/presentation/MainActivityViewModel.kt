package com.example.devlife.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.devlife.model.entities.Post
import com.example.devlife.model.repository.PostsRepository
import kotlinx.coroutines.*
import java.lang.Exception

class MainActivityViewModel(private val repository: PostsRepository): ViewModel() {

    private val _post = MutableLiveData<Post>()
    val post: LiveData<Post> get() = _post

    private val _loadingState = MutableLiveData<LoadingState>()
    val loadingState: LiveData<LoadingState> get() = _loadingState

    private val _hasPrev = MutableLiveData(false)
    val hasPrev: LiveData<Boolean>get() = _hasPrev

    private val latestPostsCache = arrayListOf<Post>()
    private var latestPostsIndex = 0

    private val coroutineScope = CoroutineScope(Job() + Dispatchers.IO)
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e(TAG, "Coroutine exception, scope active:${coroutineScope.isActive}", throwable)
        _loadingState.value = LoadingState.Error
        coroutineScope
    }

    fun newPost() {
        _loadingState.value = LoadingState.Loading
        try {
            if (latestPostsIndex == latestPostsCache.size) {
                coroutineScope.launch(exceptionHandler) {
                    val post = repository.getRandom()
                    _post.postValue(post)
                    latestPostsCache.add(post)
                }
            } else {
                _post.postValue(latestPostsCache[latestPostsIndex])
            }
            if (latestPostsIndex > 0) _hasPrev.value = true
            latestPostsIndex++

            _loadingState.value = LoadingState.Ready
        } catch (e: Exception) {
            LoadingState.Error
        }
    }

    fun prev() {
        _loadingState.value = LoadingState.Loading
        latestPostsIndex--
        if (latestPostsIndex == 1) _hasPrev.value = false
        _post.postValue(latestPostsCache[latestPostsIndex - 1])
        _loadingState.value = LoadingState.Ready

    }

    companion object {
        private val TAG = MainActivityViewModel::class.java.simpleName
    }
}