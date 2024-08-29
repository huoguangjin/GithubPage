package com.example.github_page.net

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object GithubService {
    val api by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(GithubApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(GithubApi::class.java)
    }
}
