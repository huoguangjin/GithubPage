package com.example.github_page.bean

import com.google.gson.annotations.SerializedName

data class IssueResp(
    var id: Int,
    @SerializedName("html_url")
    var htmlUrl: String,
    var title: String,
    var body: String,
)
