package com.example.flo.data.api

import com.example.flo.data.ResponseSignData
import com.example.flo.data.User
import retrofit2.Call
import retrofit2.http.*

interface SignService {
    @POST("/users")
    fun postSignUp(
        @Body body: User
    ): Call<ResponseSignData>

    @POST("/users/login")
    fun postLogin(
        @Body body: User
    ): Call<ResponseSignData>


    @GET("/users/auto-login")
    fun autoLogin(
        @Header("X-ACCESS-TOKEN") jwt: String
    ): Call<ResponseSignData>
}