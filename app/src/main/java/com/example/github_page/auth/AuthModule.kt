package com.example.github_page.auth

import android.app.Application
import com.example.github_page.net.AccessTokenInterceptor
import com.example.github_page.net.LoginApi
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
object AuthModule {
    @Provides
    @Singleton
    fun provideLoginApi(okHttpClient: OkHttpClient): LoginApi {
        val retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(LoginApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(LoginApi::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthRepo(
        app: Application,
        loginApi: LoginApi,
        interceptor: AccessTokenInterceptor,
    ): AuthRepo {
        return AuthRepoImpl(app, loginApi, interceptor)
    }
}
