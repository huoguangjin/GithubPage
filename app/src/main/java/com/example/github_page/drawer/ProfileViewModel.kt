package com.example.github_page.drawer

import android.app.Application
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.example.github_page.auth.AuthRepo
import com.example.github_page.bean.GithubUser
import com.example.github_page.net.GithubApi
import com.example.github_page.ui.LoadState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    application: Application,
    private val authRepo: AuthRepo,
    private val githubApi: GithubApi,
) : AndroidViewModel(application) {

    var viewState by mutableStateOf(ProfileState.success(null))
        private set

    val loginFlow: StateFlow<Unit?> = authRepo.isLogin.asFlow()
        .map {
            if (it) {
                getCurrentUser()
            } else {
                viewState = ProfileState.success(null)
            }
        }
        .stateIn(viewModelScope, SharingStarted.Eagerly, null)

    fun getCurrentUser() {
        val isLogin = authRepo.isLogin.value ?: false
        if (!isLogin) {
            viewState = ProfileState.success(null)
            return
        }

        viewModelScope.launch {
            try {
                viewState = ProfileState.loading(isLogin)
                val profile = githubApi.getMyProfile()
                viewState = ProfileState.success(profile)
            } catch (e: Exception) {
                viewState = ProfileState.error(e)
            }
        }
    }
}

data class ProfileState(
    val isLogin: Boolean = false,
    val loading: LoadState = LoadState.Loading,
    val user: GithubUser? = null,
) {
    companion object {
        fun loading(isLogin: Boolean) = ProfileState(isLogin, LoadState.Loading, null)
        fun success(user: GithubUser?) = ProfileState(user != null, LoadState.Success(false), user)
        fun error(e: Exception) = ProfileState(true, LoadState.Error(e), null)
    }
}
