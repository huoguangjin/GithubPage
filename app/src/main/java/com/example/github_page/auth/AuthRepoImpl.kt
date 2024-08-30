package com.example.github_page.auth

import android.app.Application
import android.content.Context
import androidx.core.content.edit
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.example.github_page.net.AccessTokenInterceptor
import com.example.github_page.net.LoginApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AuthRepoImpl(
    private val context: Application,
    private val loginApi: LoginApi,
    private val accessTokenInterceptor: AccessTokenInterceptor,
) : AuthRepo {
    companion object {
        private const val SP_NAME = "SP_AUTH"
        private const val SP_KEY_TOKEN = "SP_TOKEN"
    }

    private val accessToken = MutableLiveData<String>().apply {
        observeForever {
            accessTokenInterceptor.update(value)
        }
    }

    override val isLogin = accessToken.map {
        !it.isNullOrBlank()
    }

    init {
        accessToken.value = readAccessToken()
    }

    override fun logout() {
        GlobalScope.launch(Dispatchers.Main) {
            accessToken.value = null
            saveAccessToken(null)
        }
    }

    override suspend fun requestAccessToken(code: String) {
        withContext(Dispatchers.IO) {
            val accessTokenResp = loginApi.getAccessToken(code)
            val token = accessTokenResp.accessToken
            GlobalScope.launch(Dispatchers.Main) {
                accessToken.value = token
            }

            saveAccessToken(token)
        }
    }

    fun readAccessToken(): String? {
        val sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
        val token = sp.getString(SP_KEY_TOKEN, null)
        return token
    }

    suspend fun saveAccessToken(token: String?) {
        withContext(Dispatchers.IO) {
            val sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
            sp.edit {
                if (token != null) {
                    putString(SP_KEY_TOKEN, token)
                } else {
                    remove(SP_KEY_TOKEN)
                }
            }
        }
    }
}
