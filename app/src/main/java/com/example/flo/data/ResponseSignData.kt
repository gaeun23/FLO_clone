package com.example.flo.data

import com.google.gson.annotations.SerializedName

data class ResponseSignData(
    val code: Int,
    @SerializedName("isSuccess")
    val _isSuccess: Boolean,
    val message: String,
    val result: Auth?
)

data class Auth(
    val jwt: String,
    val userIdx: Int
)