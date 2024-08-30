package com.example.github_page.net

import com.example.github_page.auth.AuthRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetModule {
    @Provides
    @Singleton
    fun provideAccessTokenInterceptor(authRepo: dagger.Lazy<AuthRepo>): AccessTokenInterceptor {
        return AccessTokenInterceptor(authRepo)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(interceptor: AccessTokenInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideGithubApi(okHttpClient: OkHttpClient): GithubApi {
        val retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(GithubApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(GithubApi::class.java)
    }
}
