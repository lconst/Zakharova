package com.example.devlife

import android.app.Application

class PostApp : Application() {

    val networkModule by lazy { NetworkModule() }
    val cache by lazy { Cache() }

}