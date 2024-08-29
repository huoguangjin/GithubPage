package com.example.github_page.bean

import com.google.gson.annotations.SerializedName

class AccessTokenResp(
    @SerializedName("access_token")
    var accessToken: String,
    @SerializedName("token_type")
    var tokenType: String,
    var scope: String,
)
