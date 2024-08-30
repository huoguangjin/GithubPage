package com.example.github_page.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
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
import com.example.github_page.auth.AuthViewModel
import com.example.github_page.ui.RepoItem
import com.example.github_page.ui.RepoList
import com.example.github_page.ui.Routes

@Composable
fun HomePage(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    openDrawer: () -> Unit = {},
    viewModel: RepoViewModel = hiltViewModel(),
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = { HomeTopAppBar(navController, openDrawer) },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .wrapContentSize()
        ) {
            val repoList = viewModel.pagingData.collectAsLazyPagingItems()
            RepoList(
                isRefreshing = viewModel.isRefreshing,
                onRefresh = { repoList.refresh() },
                repoList = repoList,
            ) {
                items(repoList.itemCount) {
                    val repo = repoList[it] ?: return@items
                    RepoItem(repo = repo) {
                        val route = "${Routes.ISSUE}/${repo.owner.name}/${repo.name}"
                        navController.navigate(route)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopAppBar(
    navController: NavHostController,
    openDrawer: () -> Unit = {},
) {
    TopAppBar(
        title = { Text(text = stringResource(id = R.string.app_name)) },
        navigationIcon = {
            IconButton(onClick = openDrawer) {
                Icon(Icons.Filled.Menu, stringResource(id = R.string.open_drawer))
            }
        },
        actions = {
            IconButton(onClick = {
                navController.navigate(Routes.SEARCH)
            }) {
                Icon(Icons.Filled.Search, stringResource(id = R.string.menu_more))
            }
            IconButton(onClick = {
                // TODO: 2024/08/30 add settings
            }) {
                Icon(Icons.Filled.Settings, stringResource(id = R.string.menu_settings))
            }
        },
        modifier = Modifier.fillMaxWidth()
    )
}
