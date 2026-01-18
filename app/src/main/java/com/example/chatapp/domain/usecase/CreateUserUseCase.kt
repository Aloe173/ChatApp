package com.example.chatapp.domain.usecase

import com.example.chatapp.domain.model.User
import com.example.chatapp.domain.repository.IUser

class CreateUserUseCase(
    private val userRepository: IUser)
    {

    sealed class Result {
        data class Success(val user: User) : Result()
        data class Error(val user: String) : Result()
    }

    operator fun invoke(params: User): Result {

        try {
            val user = User(
                id = userRepository.getNextId(),
                name = params.name,
                login = params.login,
                passwordHash = params.passwordHash,
                isAdmin = params.isAdmin,
                isDeleted = false
            )

            val createdUser = userRepository.create(user)

            return Result.Success(createdUser)
        } catch (e: Exception) {
            return Result.Error("Не удалось создать пользователя: ${e.message}")
        }
    }
}