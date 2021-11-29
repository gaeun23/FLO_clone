package com.example.flo.data

data class ResponseSongData(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: Result
)

data class Result(
    val songs: List<SongData>
)

data class SongData(
    val songIdx: Int,
    val albumIdx: Int,
    val coverImgUrl: String,
    val singer: String,
    val title: String
)

