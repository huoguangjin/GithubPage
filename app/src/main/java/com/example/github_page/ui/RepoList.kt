package com.example.github_page.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.example.github_page.bean.Repo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T : Any> RepoList(
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    repoList: LazyPagingItems<T>,
    content: LazyListScope.() -> Unit,
) {
    PullToRefreshBox(isRefreshing = isRefreshing, onRefresh = onRefresh) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
        ) {
            content()

            if (!isRefreshing) {
                item {
                    when (repoList.loadState.append) {
                        is LoadState.Loading -> LoadingItem()
                        is LoadState.Error -> ErrorItem {
                            repoList.retry()
                        }

                        is LoadState.NotLoading -> if (repoList.loadState.append.endOfPaginationReached) {
                            NoMoreItem()
                        }

                        else -> {}
                    }
                }
            }
        }
    }
}


@Composable
fun RepoItem(
    repo: Repo,
    onClick: (Repo) -> Unit = {},
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick(repo) },
        elevation = CardDefaults.elevatedCardElevation(4.dp),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = repo.fullName,
                fontSize = 18.sp,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = repo.description ?: "",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "⭐ ${repo.stargazersCount}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}


@Composable
fun ErrorItem(retry: () -> Unit) {
    Button(
        onClick = { retry() },
        modifier = Modifier.padding(10.dp),
    ) {
        Text(text = "retry")
    }
}

@Composable
fun NoMoreItem() {
    Text(
        text = "no more data",
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(),
        textAlign = TextAlign.Center
    )
}


@Composable
fun LoadingItem() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp), contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .padding(10.dp)
                .height(50.dp)
        )
    }
}
