package com.example.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.data.local.db.dto.UserDto

@Dao
interface UserDao {

    @Query("SELECT * FROM UserDto WHERE name=:name  ")
    suspend fun findByName(name: String): UserDto

    @Query("SELECT EXISTS(SELECT * FROM UserDto WHERE name =:name)")
    suspend fun isRowExists(name: String): Boolean

    @Insert
    suspend fun insert(user: UserDto)

}