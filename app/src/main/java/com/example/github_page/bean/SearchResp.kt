package com.example.github_page.bean

import com.google.gson.annotations.SerializedName

data class SearchResp<T>(
    @SerializedName("total_count")
    var totalCount: Int,
    var items: MutableList<T>,
)
