package com.example.flo.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Song::class, Album::class, User::class, Like::class], version = 1)
abstract class SongDataBase : RoomDatabase() {
    abstract fun songDao(): SongDao
    abstract fun albumDao(): AlbumDao
    abstract fun userDao(): UserDao

    companion object {
        private var instance: SongDataBase? = null

        @Synchronized
        fun getInstance(context: Context): SongDataBase? {
            if (instance == null) {
                synchronized(SongDataBase::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        SongDataBase::class.java,
                        "user-database"
                    ).allowMainThreadQueries().build()
                }
            }
            return instance
        }
    }
}
