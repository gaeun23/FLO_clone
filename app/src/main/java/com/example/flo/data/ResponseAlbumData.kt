package com.example.flo.data

data class ResponseAlbumData(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: Result
)

data class Result(
    val albums: List<AlbumData>
)

data class AlbumData(
    val albumIdx: Int,
    val coverImgUrl: String,
    val singer: String,
    val title: String
)