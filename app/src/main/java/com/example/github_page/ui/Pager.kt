package com.example.github_page.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingSource.*
import androidx.paging.PagingState
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.Flow

fun <K : Any, V : Any> ViewModel.buildPager(
    pagingConfig: PagingConfig,
    initialKey: K,
    loadPage: suspend (params: LoadParams<K>) -> LoadResult<K, V>,
): Flow<PagingData<V>> {
    return Pager(pagingConfig, initialKey) {
        object : PagingSource<K, V>() {
            override fun getRefreshKey(state: PagingState<K, V>): K {
                return initialKey
            }

            override suspend fun load(params: LoadParams<K>): LoadResult<K, V> {
                return loadPage(params)
            }
        }
    }.flow.cachedIn(viewModelScope)
}
