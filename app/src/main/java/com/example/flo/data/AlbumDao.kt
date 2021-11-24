package com.example.flo.data

import androidx.room.*

@Dao
interface AlbumDao {
    @Insert
    fun insert(album: Album)

    @Update
    fun update(album: Album)

    @Delete
    fun delete(album: Album)

    @Query("SELECT * FROM AlbumTable")
    fun getAlbums(): List<Album>

    @Query("UPDATE AlbumTable SET isLike= :isLike WHERE id= :id")
    fun updateLike(isLike: Boolean, id: Int)

    @Insert
    fun likeAlbumInsert(like: Like)

    @Query("SELECT id FROM LikeTable WHERE userId =:userId AND albumId =:albumId ")
    fun isLikeAlbum(userId: Int, albumId: Int): Int?

    @Query("DELETE FROM LikeTable  WHERE userId =:userId AND albumId =:albumId")
    fun disLikeAlbum(userId: Int, albumId: Int)

    @Query("SELECT AT.* FROM LikeTable as LT LEFT JOIN AlbumTable as AT ON LT.albumId = AT.id WHERE LT.userId = :userId")
    fun getLikedAlbum(userId: Int): List<Album>
}