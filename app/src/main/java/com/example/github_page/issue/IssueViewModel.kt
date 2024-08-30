package com.example.github_page.issue

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.github_page.bean.IssueParam
import com.example.github_page.net.GithubApi
import com.example.github_page.ui.LoadState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class IssueViewModel @Inject constructor(
    private val api: GithubApi,
) : ViewModel() {
    var submitState by mutableStateOf<LoadState>(LoadState.Success(true))
        private set

    var issueParam by mutableStateOf(IssueParam("", ""))

    fun updateTitle(title: String) {
        issueParam = issueParam.copy(title= title)
    }

    fun updateBody(body: String) {
        issueParam = issueParam.copy(body= body)
    }

    fun submit(owner: String, repo: String) {
        if (issueParam.title.isBlank()) {
            submitState = LoadState.Error(IllegalArgumentException("title is blank"))
            return
        }

        viewModelScope.launch {
            submitState = LoadState.Loading
            try {
                api.openIssue(owner, repo, issueParam)
                submitState = LoadState.Success(false)
            } catch (e: Exception) {
                submitState = LoadState.Error(e)
            }
        }
    }
}
