package com.example.domain.use_cases.authorization.user_data

import timber.log.Timber
import javax.inject.Inject

open class LoginUserUseCase @Inject constructor(
    private val getUserByNameUseCase: GetUserByNameUseCase,
    private val addLoggedInNameToDatastoreUseCase: AddLoggedInNameToDatastoreUseCase
) {

   open suspend operator fun invoke(name: String, password: String): Result {
        Timber.d("invoke: $name")
        try {
            val userDto = getUserByNameUseCase(name)
            if (userDto.password != password) {
                Timber.e("LoginUserUseCase: failed, passwords do not match")
                return Result.Failure
            }
            addLoggedInNameToDatastoreUseCase(name)
            Timber.d("Success!")
            return Result.Success
        } catch (e: Exception) {
            Timber.e("LoginUserUseCase: failed, exception: ${e.message}")
            return Result.Failure
        }
    }

    sealed class Result {
        object Success : Result()
        object Failure : Result()
    }
}