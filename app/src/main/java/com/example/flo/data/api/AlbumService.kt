package com.example.flo.data.api

import com.example.flo.data.ResponseAlbumData
import retrofit2.Call
import retrofit2.http.GET

interface AlbumService {
    @GET("/albums")
    fun getAlbums(
    ): Call<ResponseAlbumData>
}