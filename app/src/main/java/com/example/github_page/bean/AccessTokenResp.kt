package com.example.github_page.bean

import com.google.gson.annotations.SerializedName

/**
{
  "access_token": "ghu_Ks*",
  "expires_in": 28800,
  "refresh_token": "ghr_eN*",
  "refresh_token_expires_in": 15638400,
  "token_type": "bearer",
  "scope": ""
}
 */
class AccessTokenResp(
    @SerializedName("access_token")
    var accessToken: String,
    @SerializedName("expires_in")
    var expiresIn: Long,
    @SerializedName("refresh_token")
    var refreshToken: String,
    @SerializedName("refresh_token_expires_in")
    var refreshTokenExpiresIn: Long,
    @SerializedName("token_type")
    var tokenType: String,
    var scope: String,
)
