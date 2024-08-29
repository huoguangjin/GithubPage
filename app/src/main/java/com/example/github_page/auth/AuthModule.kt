package com.example.github_page.auth

import android.app.Application
import com.example.github_page.net.GithubService
import com.example.github_page.net.LoginApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {
    @Provides
    fun provideLoginApi(): LoginApi {
        return GithubService.login
    }

    @Provides
    @Singleton
    fun provideAuthRepo(app: Application, loginApi: LoginApi): AuthRepo {
        return AuthRepoImpl(app, loginApi)
    }
}
