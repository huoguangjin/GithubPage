package com.example.github_page.net

import com.example.github_page.bean.AccessTokenResp
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST

interface LoginApi {

    companion object {
        const val BASE_URL = "https://github.com/"

        const val CLIENT_ID = "Ov23livcbzZRLTc5TtLc"
        const val CLIENT_SECRET = "e26bec142e4cac214e39d4dd3540d69ebede63cd"

        const val REDIRECT_URI = "ghp://callback"
        const val AUTHORIZE_URL =
            "https://github.com/login/oauth/authorize?client_id=${CLIENT_ID}&redirect_uri=${REDIRECT_URI}&scope=user,issue"
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
