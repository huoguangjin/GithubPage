package com.example.github_page.ui

object Routes {
    const val HOME = "home"
    const val DASHBOARD = "dashboard"
    const val ISSUE = "issue"
    const val SEARCH = "search"
    const val LOGIN = "login"
    const val SETTINGS = "settings"

    const val ISSUE_OWNER_ARG = "owner"
    const val ISSUE_REPO_ARG = "repo"
    const val ISSUE_ROUTE = "$ISSUE/{$ISSUE_OWNER_ARG}/{$ISSUE_REPO_ARG}"
}
