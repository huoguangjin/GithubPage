package com.example.github_page.auth

import androidx.lifecycle.LiveData

interface AuthRepo {
    val isLogin: LiveData<Boolean>

    fun logout()

    suspend fun requestAccessToken(code: String)
}
