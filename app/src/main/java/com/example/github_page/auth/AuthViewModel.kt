package com.example.github_page.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepo: AuthRepo,
) : ViewModel() {

    val isLogin: LiveData<Boolean> = authRepo.isLogin

    fun logout() {
        authRepo.logout()
    }

    fun getAccessToken(code: String) {
        viewModelScope.launch {
            authRepo.requestAccessToken(code)
        }
    }
}
