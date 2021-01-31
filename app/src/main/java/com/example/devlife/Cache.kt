package com.example.devlife

import com.example.devlife.model.entities.Post

class Cache {

    private val postsCache = arrayListOf<Post>()
    private var postsIndex = 0

    fun put(post: Post) {
        postsCache.add(post)
        postsIndex = postsCache.size - 1
    }

    fun getPrev(): Post {
        return postsCache[--postsIndex]
    }

    fun getNext(): Post {
        return postsCache[++postsIndex]
    }

    fun hasNext(): Boolean {
        return postsIndex < postsCache.size - 1
    }

    fun hasPrev(): Boolean {
        return postsIndex > 0
    }
}