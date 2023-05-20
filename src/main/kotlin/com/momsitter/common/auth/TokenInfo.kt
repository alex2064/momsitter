package com.momsitter.common.auth

data class TokenInfo(
    val grantType: String,
    val accessToken: String,
    val refreshToken: String,
)