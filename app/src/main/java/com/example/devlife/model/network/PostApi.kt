package com.example.devlife.model.network

import com.example.devlife.model.entities.Post
import retrofit2.http.GET
import retrofit2.http.Query

interface PostApi {

    @GET("random")
    suspend fun getRandomPost(@Query("json") json: Boolean = true ): Post
}