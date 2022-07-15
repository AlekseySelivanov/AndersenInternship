package com.example.domain.use_cases.common

import com.example.domain.use_cases.validation.*
import javax.inject.Inject

data class ValidationCases @Inject constructor(
    val validateFullName: ValidateFullName,
    val validateEmail: ValidateEmail,
    val validatePhoneNumber: ValidatePhoneNumber,
    val validatePassword: ValidatePassword,
    val validateRepeatedPassword: ValidateRepeatedPassword
)
