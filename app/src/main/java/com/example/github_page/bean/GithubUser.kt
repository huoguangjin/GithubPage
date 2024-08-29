package com.example.github_page.bean

import com.google.gson.annotations.SerializedName


/**
{
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
}
 */
data class GithubUser(
    @SerializedName("login")
    var name: String,

    var id: String,

    @SerializedName("avatar_url")
    var avatarUrl: String,

    @SerializedName("html_url")
    var htmlUrl: String,
)
