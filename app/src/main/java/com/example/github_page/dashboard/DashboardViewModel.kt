package com.example.github_page.dashboard

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource.LoadResult
import com.example.github_page.bean.Repo
import com.example.github_page.net.GithubApi
import com.example.github_page.ui.buildPager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val api: GithubApi,
) : ViewModel() {
    var isRefreshing by mutableStateOf(false)
        private set

    val pagingData: Flow<PagingData<Repo>> by lazy {
        val pageSize = 30
        val pagingConfig = PagingConfig(
            pageSize,
        )
        val initialKey = 1
        buildPager(pagingConfig, initialKey) { params ->
            val page = params.key ?: 1
            return@buildPager try {
                isRefreshing = true

                val repos = api.getMyRepoList(page)
                val hasMore = repos.size >= params.loadSize
                LoadResult.Page(
                    repos,
                    prevKey = if (page - 1 > 0) page - 1 else null,
                    nextKey = if (hasMore) page + 1 else null,
                )
            } catch (e: Exception) {
                LoadResult.Error(e)
            } finally {
                isRefreshing = false
            }
        }
    }
}
