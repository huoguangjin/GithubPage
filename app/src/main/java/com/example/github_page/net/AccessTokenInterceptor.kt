package com.example.github_page.net

import okhttp3.Interceptor
import okhttp3.Response

class AccessTokenInterceptor(
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
            //
        }

        return response
    }

    fun update(newToken: String?) {
        accessToken = newToken
    }
}
