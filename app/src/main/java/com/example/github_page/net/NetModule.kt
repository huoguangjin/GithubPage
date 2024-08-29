package com.example.github_page.net

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object NetModule {
    @Provides
    fun provideGithubApi(): GithubApi {
        return GithubService.api
    }
}
