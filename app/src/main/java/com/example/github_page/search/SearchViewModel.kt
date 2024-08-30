package com.example.github_page.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource.*
import com.example.github_page.bean.Repo
import com.example.github_page.net.GithubApi
import com.example.github_page.ui.buildPager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val api: GithubApi,
) : ViewModel() {
    var isRefreshing by mutableStateOf(false)
        private set

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> get() = _query

    private val _sortType = MutableStateFlow(SearchSortType.SORT_STARS)
    val sortType: StateFlow<String> get() = _sortType

    var pagingData: Flow<PagingData<Repo>> = MutableStateFlow(PagingData.empty())

    fun updateSortType(newSortType: String) {
        _sortType.value = newSortType
        search()
    }

    fun updateQuery(newQuery: String) {
        _query.value = newQuery
        search()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun search() {
        viewModelScope.launch {
            pagingData = query.flatMapLatest { query ->
                if (query.isEmpty()) {
                    flowOf(PagingData.empty())
                } else {
                    createPager(query)
                }
            }.stateIn(viewModelScope)
        }
    }

    fun createPager(query: String): Flow<PagingData<Repo>> {
        val pagingConfig = PagingConfig(30)
        val initialKey = 1

        return buildPager(pagingConfig, initialKey) { params ->
            val page = params.key ?: 1
            return@buildPager try {
                isRefreshing = true
                val resp = api.searchRepoList(query, sortType.value)
                val repos = resp.items
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

object SearchSortType {
    const val SORT_STARS = "stars"
    const val SORT_FORKS = "forks"
    const val SORT_UPDATED = "updated"
}
