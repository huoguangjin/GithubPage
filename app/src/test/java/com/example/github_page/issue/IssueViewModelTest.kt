package com.example.github_page.issue

import com.example.github_page.MainCoroutineRule
import com.example.github_page.bean.IssueParam
import com.example.github_page.net.GithubApi
import com.example.github_page.ui.LoadState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.kotlin.eq
import org.mockito.kotlin.givenBlocking
import org.mockito.kotlin.mock
import org.mockito.kotlin.never
import org.mockito.kotlin.verify

class IssueViewModelTest {
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Mock
    var githubApi: GithubApi = mock<GithubApi>()

    private lateinit var issueViewModel: IssueViewModel

    @Before
    fun setupViewModel() {
        issueViewModel = IssueViewModel(githubApi)
    }

    @Test
    fun test_issueParam() = runTest {
        val title = "fake title"
        val body = "fake body"

        issueViewModel.updateTitle(title)
        issueViewModel.updateBody(body)

        Assert.assertEquals(IssueParam(title, body), issueViewModel.issueParam)
    }

    @Test
    fun `submit and call createIssue`() = runTest {
        Dispatchers.setMain(StandardTestDispatcher())

        val title = "fake title"
        val body = "fake body"
        val owner = "fake owner"
        val repo = "fake repo"

        issueViewModel.updateTitle(title)
        issueViewModel.updateBody(body)
        issueViewModel.submit(owner, repo)

        advanceUntilIdle()

        verify(githubApi).createIssue(eq(owner), eq(repo), eq(IssueParam(title, body)))
    }

    @Test
    fun `submit empty title`() = runTest {
        Dispatchers.setMain(StandardTestDispatcher())

        val title = ""
        val body = "fake body"
        val owner = "fake owner"
        val repo = "fake repo"

        issueViewModel.updateTitle(title)
        issueViewModel.updateBody(body)
        issueViewModel.submit(owner, repo)

        advanceUntilIdle()

        verify(githubApi, never()).createIssue(eq(owner), eq(repo), eq(IssueParam(title, body)))

        val submitState = issueViewModel.submitState
        Assert.assertTrue(submitState is LoadState.Error)
    }

    @Test
    fun `submit success`() = runTest {
        Dispatchers.setMain(StandardTestDispatcher())

        val title = "fake title"
        val body = "fake body"
        val owner = "fake owner"
        val repo = "fake repo"

        issueViewModel.updateTitle(title)
        issueViewModel.updateBody(body)
        issueViewModel.submit(owner, repo)

        val error = Exception("fake error")
        givenBlocking {
            githubApi.createIssue(owner, repo, IssueParam(title, body))
        }.willAnswer {
            throw error
        }

        advanceUntilIdle()

        val submitState = issueViewModel.submitState
        Assert.assertTrue(submitState is LoadState.Error && submitState.exception == error)
    }
    @Test
    fun `submit error`() = runTest {
        Dispatchers.setMain(StandardTestDispatcher())

        val title = "fake title"
        val body = "fake body"
        val owner = "fake owner"
        val repo = "fake repo"

        issueViewModel.updateTitle(title)
        issueViewModel.updateBody(body)
        issueViewModel.submit(owner, repo)

        advanceUntilIdle()

        val submitState = issueViewModel.submitState
        Assert.assertTrue(submitState is LoadState.Success)
    }
}
