package com.example.chatapp.domain.usecase

import com.example.chatapp.domain.model.RegistrationRequest

class RegistrationValidator {
    fun validate(request: RegistrationRequest): ValidationResult {
        val errors = mutableListOf<String>()

        if (request.username.length < 3) {
            errors.add("Минимальная длина имени пользователя - 4 символа")
        }

        if (request.password.length < 8) {
            errors.add("Минимальная длина пароля - 8 символов")
        }

        if (request.username.any { it !in 'a'..'z' && it !in 'A'..'Z' && it !in '0'..'9' }) {
            errors.add("Имя пользователя содержит недопустимые символы")

            if (request.password.any { it !in 'a'..'z' && it !in 'A'..'Z' && it !in '0'..'9' }) {
                errors.add("Пароль содержит недопустимые символы")

            }

            return if (errors.isEmpty()) {
                ValidationResult.Valid
            } else {
                ValidationResult.Invalid(errors)
            }
        }
    }

    sealed class ValidationResult {
        object Valid : ValidationResult()
        data class Invalid(val errors: List<String>) : ValidationResult()
    }