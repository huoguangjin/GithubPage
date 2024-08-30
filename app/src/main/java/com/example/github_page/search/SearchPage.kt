package com.example.github_page.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.github_page.R
import com.example.github_page.ui.RepoItem
import com.example.github_page.ui.RepoList


@Composable
fun SearchPage(
    modifier: Modifier = Modifier,
    searchViewModel: SearchViewModel = hiltViewModel(),
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            SearchTopAppBar {
                searchViewModel.updateSortType(it)
            }
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .wrapContentSize()
        ) {
            val query by searchViewModel.query.collectAsStateWithLifecycle()

            Row(modifier = Modifier.padding(8.dp)) {
                OutlinedTextField(
                    value = query,
                    onValueChange = { searchViewModel.updateQuery(it) },
                    label = { Text("Search Repositories") },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // Spacer(modifier = Modifier.height(16.dp))

            val repoList = searchViewModel.pagingData.collectAsLazyPagingItems()
            RepoList(
                isRefreshing = searchViewModel.isRefreshing,
                onRefresh = { repoList.refresh() },
                repoList = repoList,
            ) {
                items(repoList.itemCount) {
                    val repo = repoList[it] ?: return@items
                    RepoItem(repo = repo)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchTopAppBar(
    onChangeSortType: (sortType: String) -> Unit,
) {
    TopAppBar(
        title = { Text(text = stringResource(id = R.string.page_search)) },
        actions = {
            SortMenu(onChangeSortType = onChangeSortType)
        },
        modifier = Modifier.fillMaxWidth()
    )
}


@Composable
private fun SortMenu(
    onChangeSortType: (sortType: String) -> Unit,
) {
    TopAppBarDropdownMenu(
        iconContent = {
            Icon(
                painterResource(id = R.drawable.ic_sort),
                stringResource(id = R.string.menu_sort)
            )
        }
    ) { closeMenu ->
        DropdownMenuItem(
            text = { Text(text = stringResource(id = R.string.sort_stars)) },
            onClick = { onChangeSortType(SearchSortType.SORT_STARS); closeMenu() }
        )
        DropdownMenuItem(
            text = { Text(text = stringResource(id = R.string.sort_forks)) },
            onClick = { onChangeSortType(SearchSortType.SORT_FORKS); closeMenu() }
        )
        DropdownMenuItem(
            text = { Text(text = stringResource(id = R.string.sort_updated)) },
            onClick = { onChangeSortType(SearchSortType.SORT_UPDATED); closeMenu() }
        )
    }
}

@Composable
private fun TopAppBarDropdownMenu(
    iconContent: @Composable () -> Unit,
    content: @Composable ColumnScope.(() -> Unit) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier.wrapContentSize(Alignment.TopEnd)) {
        IconButton(onClick = { expanded = !expanded }) {
            iconContent()
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.wrapContentSize(Alignment.TopEnd)
        ) {
            content { expanded = !expanded }
        }
    }
}
