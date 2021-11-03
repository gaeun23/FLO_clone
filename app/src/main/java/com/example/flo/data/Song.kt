package com.example.flo.data

data class Song(
    var title: String = "",
    var singer: String = "",
    var playTime: Int = 0,
    var isPlaying: Boolean = false,
    var currentMillis: Int = 0,
    var music: String = "",
    var second: Int = 0
)
