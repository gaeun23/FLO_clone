package com.example.flo.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "SongTable")
data class Song(
    var title: String = "",
    var singer: String = "",
    var playTime: Int = 0,
    var isPlaying: Boolean = false,
    var music: String = "",
    var second: Int = 0,
    var coverImg: Int? = null,
    var isLike: Boolean = false
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}