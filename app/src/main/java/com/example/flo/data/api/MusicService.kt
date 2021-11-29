package com.example.flo.data.api

import com.example.flo.data.ResponseSongData
import retrofit2.Call
import retrofit2.http.GET

interface MusicService {
    @GET("/songs")
    fun getSongs(
    ): Call<ResponseSongData>
}