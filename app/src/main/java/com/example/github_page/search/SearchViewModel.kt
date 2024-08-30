package com.example.github_page.search

import androidx.lifecycle.ViewModel
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource.LoadResult
import com.example.github_page.bean.Repo
import com.example.github_page.net.GithubApi
import com.example.github_page.ui.buildPager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val api: GithubApi,
) : ViewModel() {
    var isRefreshing = MutableStateFlow(false)
        private set

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> get() = _query

    private val _sortType = MutableStateFlow(SearchSortType.SORT_STARS)
    val sortType: StateFlow<String> get() = _sortType

    @OptIn(ExperimentalCoroutinesApi::class)
    val pagingData: Flow<PagingData<Repo>> = query.filter {
        it.isNotBlank()
    }.combine(sortType) { q, s ->
        q to s
    }.flatMapLatest { (q, s) ->
        isRefreshing.value = true
        delay(400) // delay search
        val pager = createPager(q, s)
        pager.also {
            isRefreshing.value = false
        }
    }

    fun updateSortType(newSortType: String) {
        _sortType.value = newSortType
    }

    fun updateQuery(newQuery: String) {
        _query.value = newQuery
    }

    fun createPager(query: String, sortType: String): Flow<PagingData<Repo>> {
        val pageSize = 30
        val pagingConfig = PagingConfig(
            pageSize = pageSize,
            initialLoadSize = pageSize,
        )
        val initialKey = 1

        return buildPager(pagingConfig, initialKey) { params ->
            val page = params.key ?: 1
            return@buildPager try {
                val resp = api.searchRepoList(query, sortType)
                val repos = resp.items
                val hasMore = repos.size >= params.loadSize
                LoadResult.Page(
                    repos,
                    prevKey = if (page - 1 > 0) page - 1 else null,
                    nextKey = if (hasMore) page + 1 else null,
                )
            } catch (e: Exception) {
                LoadResult.Error(e)
            }
        }
    }
}

object SearchSortType {
    const val SORT_STARS = "stars"
    const val SORT_FORKS = "forks"
    const val SORT_UPDATED = "updated"
}
