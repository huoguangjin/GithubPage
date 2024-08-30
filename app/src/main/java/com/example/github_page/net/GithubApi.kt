package com.example.github_page.net

import com.example.github_page.bean.GithubUser
import com.example.github_page.bean.IssueParam
import com.example.github_page.bean.IssueResp
import com.example.github_page.bean.Repo
import com.example.github_page.bean.SearchResp
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApi {

    companion object {
        const val BASE_URL = "https://api.github.com/"
    }

    @GET("users/{username}/repos")
    suspend fun getUserRepoList(
        @Path("username") username: String,
        @Query("page") page: Int = 1,
    ): List<Repo>

    @GET("search/repositories")
    suspend fun searchRepoList(
        @Query("q") query: String,
        @Query("sort") sort: String?,
    ): SearchResp<Repo>

    @POST("user")
    @Headers("Accept: application/json")
    suspend fun getMyProfile(): GithubUser

    @GET("user/repos")
    suspend fun getMyRepoList(@Query("page") page: Int): List<Repo>

    @POST("repos/{owner}/{repo}/issues")
    suspend fun openIssue(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Body issue: IssueParam,
    ): IssueResp
}
