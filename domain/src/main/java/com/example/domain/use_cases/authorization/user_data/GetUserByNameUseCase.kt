package com.example.domain.use_cases.authorization.user_data

import com.example.data.local.db.dao.UserDao
import com.example.data.local.db.dto.UserDto
import timber.log.Timber
import javax.inject.Inject

open class GetUserByNameUseCase @Inject constructor(private val userDao: UserDao) {

   open suspend operator fun invoke(name: String): UserDto {
        Timber.d("invoke: $name")
        return userDao.findByName(name)
    }
}