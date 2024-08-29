package com.example.github_page.net

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object GithubService {
    val accessTokenInterceptor = AccessTokenInterceptor()

    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(accessTokenInterceptor)
        .build()

    val api by lazy {
        val retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(GithubApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(GithubApi::class.java)
    }

    val login by lazy {
        val retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(LoginApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(LoginApi::class.java)
    }
}
