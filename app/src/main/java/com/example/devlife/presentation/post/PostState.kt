package com.example.devlife.presentation.post

sealed class PostState {
    object Loading : PostState()
    object Ready : PostState()
    object Error : PostState()
}