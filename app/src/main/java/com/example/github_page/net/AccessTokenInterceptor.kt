package com.example.github_page.net

import com.example.github_page.auth.AuthRepo
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AccessTokenInterceptor @Inject constructor(
    private val authRepo: dagger.Lazy<AuthRepo>,
    private var accessToken: String? = null
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        val headerKey = "Authorization"
        if (request.header(headerKey) == null && !accessToken.isNullOrBlank()) {
            request = request.newBuilder()
                .addHeader(headerKey, "Bearer $accessToken")
                .build()
        }

        val response = chain.proceed(request)

        if (response.code() == 401) {
            invalidToken()
        }

        return response
    }

    fun update(newToken: String?) {
        accessToken = newToken
    }

    private fun invalidToken() {
        authRepo.get().logout()
    }
}
