package com.example.github_page.bean

import com.google.gson.annotations.SerializedName

/**
{
  "url": "https://api.github.com/repos/huoguangjin/TestIssue/issues/3",
  "repository_url": "https://api.github.com/repos/huoguangjin/TestIssue",
  "labels_url": "https://api.github.com/repos/huoguangjin/TestIssue/issues/3/labels{/name}",
  "comments_url": "https://api.github.com/repos/huoguangjin/TestIssue/issues/3/comments",
  "events_url": "https://api.github.com/repos/huoguangjin/TestIssue/issues/3/events",
  "html_url": "https://github.com/huoguangjin/TestIssue/issues/3",
  "id": 2498974489,
  "node_id": "I_kwDOMrA_jc6U81MZ",
  "number": 3,
  "title": "test api",
  "user": {
    "login": "huoguangjin",
    "id": 22366182,
    "node_id": "MDQ6VXNlcjIyMzY2MTgy",
    "avatar_url": "https://avatars.githubusercontent.com/u/22366182?v=4",
    "gravatar_id": "",
    "url": "https://api.github.com/users/huoguangjin",
    "html_url": "https://github.com/huoguangjin",
    "followers_url": "https://api.github.com/users/huoguangjin/followers",
    "following_url": "https://api.github.com/users/huoguangjin/following{/other_user}",
    "gists_url": "https://api.github.com/users/huoguangjin/gists{/gist_id}",
    "starred_url": "https://api.github.com/users/huoguangjin/starred{/owner}{/repo}",
    "subscriptions_url": "https://api.github.com/users/huoguangjin/subscriptions",
    "organizations_url": "https://api.github.com/users/huoguangjin/orgs",
    "repos_url": "https://api.github.com/users/huoguangjin/repos",
    "events_url": "https://api.github.com/users/huoguangjin/events{/privacy}",
    "received_events_url": "https://api.github.com/users/huoguangjin/received_events",
    "type": "User",
    "site_admin": false
  },
  "labels": [],
  "state": "open",
  "locked": false,
  "assignee": null,
  "assignees": [],
  "milestone": null,
  "comments": 0,
  "created_at": "2024-08-31T17:44:53Z",
  "updated_at": "2024-08-31T17:44:53Z",
  "closed_at": null,
  "author_association": "OWNER",
  "active_lock_reason": null,
  "body": "issue body",
  "closed_by": null,
  "reactions": {
    "url": "https://api.github.com/repos/huoguangjin/TestIssue/issues/3/reactions",
    "total_count": 0,
    "+1": 0,
    "-1": 0,
    "laugh": 0,
    "hooray": 0,
    "confused": 0,
    "heart": 0,
    "rocket": 0,
    "eyes": 0
  },
  "timeline_url": "https://api.github.com/repos/huoguangjin/TestIssue/issues/3/timeline",
  "performed_via_github_app": {
    "id": 984386,
    "client_id": "Iv23ligktWCSYDe2JP51",
    "slug": "ghp-app",
    "node_id": "A_kwDOAVVH5s4ADwVC",
    "owner": {
      "login": "huoguangjin",
      "id": 22366182,
      "node_id": "MDQ6VXNlcjIyMzY2MTgy",
      "avatar_url": "https://avatars.githubusercontent.com/u/22366182?v=4",
      "gravatar_id": "",
      "url": "https://api.github.com/users/huoguangjin",
      "html_url": "https://github.com/huoguangjin",
      "followers_url": "https://api.github.com/users/huoguangjin/followers",
      "following_url": "https://api.github.com/users/huoguangjin/following{/other_user}",
      "gists_url": "https://api.github.com/users/huoguangjin/gists{/gist_id}",
      "starred_url": "https://api.github.com/users/huoguangjin/starred{/owner}{/repo}",
      "subscriptions_url": "https://api.github.com/users/huoguangjin/subscriptions",
      "organizations_url": "https://api.github.com/users/huoguangjin/orgs",
      "repos_url": "https://api.github.com/users/huoguangjin/repos",
      "events_url": "https://api.github.com/users/huoguangjin/events{/privacy}",
      "received_events_url": "https://api.github.com/users/huoguangjin/received_events",
      "type": "User",
      "site_admin": false
    },
    "name": "ghp app",
    "description": "A simple GitHub client APP",
    "external_url": "https://github.com/huoguangjin/GithubPage",
    "html_url": "https://github.com/apps/ghp-app",
    "created_at": "2024-08-31T09:24:23Z",
    "updated_at": "2024-08-31T15:06:07Z",
    "permissions": {
      "issues": "write",
      "metadata": "read",
      "pull_requests": "write"
    },
    "events": []
  },
  "state_reason": null
}
 */
data class Issue(
    var id: String,
    var number: Int,

    @SerializedName("html_url")
    var htmlUrl: String,

    var title: String,
    var body: String,

    var state: String,

    var user: GithubUser,

    @SerializedName("created_at")
    var createdAt: String,

    @SerializedName("updated_at")
    var updatedAt: String,
)
