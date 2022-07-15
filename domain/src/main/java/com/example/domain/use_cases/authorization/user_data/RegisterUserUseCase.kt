package com.example.domain.use_cases.authorization.user_data

import com.example.data.local.db.dto.UserDto
import timber.log.Timber
import javax.inject.Inject

open class RegisterUserUseCase @Inject constructor(
    private val addUserToDatabaseUseCase: AddUserToDatabaseUseCase,
    private val checkIfUserExistsUseCase: CheckIfUserExistsUseCase
) {
   open suspend operator fun invoke(name: String, password: String): Result {
        Timber.d("invoke: $name")
        val userExists = checkIfUserExistsUseCase(name)
        return if (!userExists) {
            addUserToDatabaseUseCase(UserDto(name = name, password = password))
            Timber.d("Success!")
            Result.Success
        } else {
            Timber.d("Failure")
            Result.Failure
        }
    }

    sealed class Result {
        object Success : Result()
        object Failure : Result()
    }
}