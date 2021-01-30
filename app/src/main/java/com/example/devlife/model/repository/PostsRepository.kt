package com.example.devlife.model.repository

import com.example.devlife.model.entities.Post
import com.example.devlife.model.network.PostApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PostsRepository(private val postApi: PostApi) {

    suspend fun getRandom(): Post {
        return withContext(Dispatchers.IO) {
            postApi.getRandomPost()
        }
    }
}