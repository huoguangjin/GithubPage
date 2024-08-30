package com.example.github_page.dashboard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.github_page.R
import com.example.github_page.ui.RepoItem
import com.example.github_page.ui.RepoList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardPage(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    openDrawer: () -> Unit = {},
    dashboardViewModel: DashboardViewModel = hiltViewModel(),
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.page_dashboard)) },
                navigationIcon = {
                    IconButton(onClick = openDrawer) {
                        Icon(Icons.Filled.Menu, stringResource(id = R.string.open_drawer))
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .wrapContentSize()
        ) {
            val repoList = dashboardViewModel.pagingData.collectAsLazyPagingItems()
            RepoList(
                isRefreshing = dashboardViewModel.isRefreshing,
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
