package com.example.data.local.db.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserDto(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    val name: String,
    val password: String
)