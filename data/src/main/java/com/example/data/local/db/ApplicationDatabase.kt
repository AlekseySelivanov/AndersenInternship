package com.example.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.local.db.dao.UserDao
import com.example.data.local.db.dto.UserDto

@Database(entities = [UserDto::class], version = 1)
abstract class ApplicationDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

}