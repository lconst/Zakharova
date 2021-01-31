package com.example.devlife.presentation.post

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.devlife.Cache
import com.example.devlife.model.entities.Post
import com.example.devlife.model.repository.PostsRepository
import kotlinx.coroutines.*
import java.lang.Exception
import java.util.concurrent.CancellationException

class PostViewModel(
    private val cache: Cache,
    private val repository: PostsRepository): ViewModel() {

    private val _post = MutableLiveData<Post>()
    val post: LiveData<Post> get() = _post

    private val _postState = MutableLiveData<PostState>()
    val postState: LiveData<PostState> get() = _postState

    private val _hasPrev = MutableLiveData(false)
    val hasPrev: LiveData<Boolean> get() = _hasPrev

    private var coroutineScope = createCoroutineScope()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e(TAG, "Coroutine exception, scope active:${coroutineScope.isActive}", throwable)
        coroutineScope = createCoroutineScope()
        _postState.postValue(PostState.Error)
    }

    fun nextPost() {
        _postState.postValue(PostState.Loading)
        if (!cache.hasNext()) {
            loadNewPost()
        } else {
            _post.postValue(cache.getNext())
            _postState.postValue(PostState.Ready)
        }
        checkPrev()
    }

    private fun loadNewPost() {
        try {
            coroutineScope.launch(exceptionHandler) {
                val post = repository.getRandom()
                _post.postValue(post)
                cache.put(post)
                _postState.postValue(PostState.Ready)
            }
        } catch (e: Exception) {
            if (e is CancellationException) {
                throw e
            } else {
                Log.w(PostViewModel::class.java.name, e)
            }
        }
    }

    fun prev() {
        _postState.postValue(PostState.Loading)
        _post.postValue(cache.getPrev())
        checkPrev()
        _postState.postValue(PostState.Ready)
    }

    private fun checkPrev() {
        _hasPrev.postValue(cache.hasPrev())
    }

    private fun createCoroutineScope() = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    companion object {
        private val TAG = PostViewModel::class.java.simpleName
    }
}