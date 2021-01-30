package com.example.devlife.presentation

sealed class LoadingState {
    object Loading : LoadingState()
    object Ready : LoadingState()
    object Error : LoadingState()
}