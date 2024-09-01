package com.example.github_page.issue

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.github_page.R
import com.example.github_page.ui.LoadState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IssuePage(
    navController: NavHostController,
    owner: String,
    repo: String,
    modifier: Modifier = Modifier,
    issueViewModel: IssueViewModel = hiltViewModel(),
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.title_issue)) },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigateUp()
                    }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            stringResource(id = R.string.menu_back)
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
        },
    ) { innerPadding ->
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            val issueParam = issueViewModel.issueParam
            val submitState = issueViewModel.submitState
            when (submitState) {
                LoadState.Loading -> {
                    LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
                }

                is LoadState.Success -> {
                    val hint = stringResource(id = R.string.hint_create_issue_success)
                    LaunchedEffect(submitState) {
                        if (!submitState.isEmpty) {
                            scope.launch { snackbarHostState.showSnackbar(hint) }
                        }
                    }
                }

                is LoadState.Error -> {
                    val message = submitState.exception.message ?: submitState.exception.toString()
                    val hint = stringResource(id = R.string.hint_create_issue_failed, message)
                    LaunchedEffect(submitState) {
                        scope.launch { snackbarHostState.showSnackbar(hint) }
                    }
                }
            }

            Text(
                text = stringResource(id = R.string.hint_create_issue, "$owner/$repo"),
                fontSize = 18.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = issueParam.title, onValueChange = {
                    issueViewModel.updateTitle(it)
                },
                label = {
                    Text(stringResource(id = R.string.label_issue_title))
                }
            )
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                value = issueParam.body, onValueChange = {
                    issueViewModel.updateBody(it)
                },
                label = {
                    Text(stringResource(id = R.string.label_issue_content))
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { issueViewModel.submit(owner, repo) },
                enabled = (submitState !is LoadState.Loading)
            ) {
                Text(stringResource(id = R.string.label_issue_submit))
            }
        }
    }
}
