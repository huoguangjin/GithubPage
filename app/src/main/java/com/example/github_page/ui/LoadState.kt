package com.example.github_page.ui

sealed class LoadState {
    data object Loading : LoadState()
    data class Success(val isEmpty: Boolean) : LoadState()
    data class Error(val exception: Throwable) : LoadState()
}
