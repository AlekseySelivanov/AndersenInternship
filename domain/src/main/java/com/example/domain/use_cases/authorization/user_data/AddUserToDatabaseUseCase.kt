package com.example.domain.use_cases.authorization.user_data

import com.example.data.local.db.dao.UserDao
import com.example.data.local.db.dto.UserDto
import timber.log.Timber
import javax.inject.Inject

class AddUserToDatabaseUseCase @Inject constructor(
    private val userDao: UserDao
) {

    suspend operator fun invoke(userDto: UserDto) {
        Timber.d("invoke!")
        userDao.insert(userDto)
    }
}