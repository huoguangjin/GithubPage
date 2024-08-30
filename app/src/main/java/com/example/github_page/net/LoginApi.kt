package com.example.github_page.net

import com.example.github_page.bean.AccessTokenResp
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST

interface LoginApi {

    companion object {
        const val BASE_URL = "https://github.com/"

        const val CLIENT_ID = "Iv23ligktWCSYDe2JP51"
        const val CLIENT_SECRET = "402631124f5504a037683b38d13c8b6ec4ca2850"

        const val REDIRECT_URI = "ghp://callback"
        const val AUTHORIZE_URL =
            "https://github.com/login/oauth/authorize?client_id=${CLIENT_ID}&redirect_uri=${REDIRECT_URI}"
    }

    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST("login/oauth/access_token")
    suspend fun getAccessToken(
        @Field("code") code: String,
        @Field("client_id") clientId: String = CLIENT_ID,
        @Field("client_secret") clientSecret: String = CLIENT_SECRET,
    ): AccessTokenResp
}
