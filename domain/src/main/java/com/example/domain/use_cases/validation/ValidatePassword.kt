package com.example.domain.use_cases.validation

import com.example.domain.model.ValidationResult
import com.example.domain.repository.StringsRepository
import java.util.regex.Pattern
import javax.inject.Inject

class ValidatePassword @Inject constructor(
    private val stringsRepository: StringsRepository
) {
    companion object {
        private const val PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[!])(?=\\S+$).{4,}$"
    }

    operator fun invoke(password: String): ValidationResult {
        if (password.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = stringsRepository.blankPasswordMessage
            )
        }

        if (!Pattern.compile(PASSWORD_PATTERN)
                .matcher(password)
                .matches()
        ) {
            return ValidationResult(
                successful = false,
                errorMessage = stringsRepository.incorrectPasswordFormatExc
            )
        }
        val containsLettersAndDigits =
            password.any { it.isDigit() } && password.any { it.isLetter() }
        if (!containsLettersAndDigits) {
            return ValidationResult(
                successful = false,
                errorMessage = stringsRepository.incorrectPasswordFormat
            )
        }
        return ValidationResult(successful = true)
    }
}