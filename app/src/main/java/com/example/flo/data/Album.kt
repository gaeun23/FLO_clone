package com.example.flo.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "AlbumTable")
data class Album(
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0,
    var cover: Int? = null,
    var title: String? = "",
    var singer: String? = "",
    var isLike: Boolean? = false,
    var info: String? = ""
)
