package com.example.chatapp.domain.usecase

import com.example.chatapp.domain.model.User
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder     //Тут фрейм на хеширование, хз как правильно его импортировать

sealed class RegistrationError : RuntimeException() {
    class ValidationError(val errors: List<String>) : RegistrationError()
    class UsernameAlreadyExists : RegistrationError()
    class RegistrationFailed(cause: Throwable?) : RegistrationError()
}

class RegisterUseCase(
    private val userRepository: UserRepository,
    private val validator: RegValidator,
    private val passwordEncoder = BCryptPasswordEncoder()
) {
    suspend operator fun invoke(request: RegistrationRequest): Result<RegistrationResponse> {
        return try {

            when (val validation = validator.validate(request)) {
                is ValidationResult.Invalid -> return Result.failure(ValidationException(validation.errors))
                is ValidationResult.Valid -> Unit
            }

            if (userRepository.existsByUsername(request.username)) {
                return Result.failure(UsernameAlreadyExistsException())
            }

            val user = User(
                name = request.name,
                username = request.username,
                passwordHash = passwordEncoder.encode(request.password)
            )

            val savedUser = userRepository.create(user)

            Result.success(
                RegistrationResponse(
                    user = savedUser
                )
            )
        } catch (e: Exception) {
            Result.failure(RegistrationFailedException("Registration failed", e))
        }
    }
}