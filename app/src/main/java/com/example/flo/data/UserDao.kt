package com.example.flo.data

import androidx.room.*

@Dao
interface UserDao {
    @Insert
    fun insert(user: User)

    @Query("SELECT * FROM UserTable")
    fun getUsers(): List<User>

    @Query("SELECT * FROM userTable WHERE email= :email AND password =:password ")
    fun getUser(email: String, password: String): User?
}