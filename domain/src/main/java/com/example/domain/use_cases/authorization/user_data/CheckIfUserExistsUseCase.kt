package com.example.domain.use_cases.authorization.user_data

import com.example.data.local.db.dao.UserDao
import timber.log.Timber
import javax.inject.Inject

class CheckIfUserExistsUseCase @Inject constructor(
    private val userDao: UserDao
) {
    suspend operator fun invoke(name: String): Boolean {
        Timber.d("invoke $name")
       return userDao.isRowExists(name)
    }
}