package com.example.chatapp.domain.usecase

import com.example.chatapp.domain.model.User
import com.example.chatapp.domain.repository.IUser

class UpdateUserPasswordUseCase(
    private val userRepository: IUser,
    private val newPassword: String
) {

    data class Params(
        val userId: Int
    )

    sealed class Result {
        data class Success(val user: User) : Result()
        data class Error(val message: String) : Result()
    }

    operator fun invoke(params: Params): Result {

        val existingUser = userRepository.getUser(params.userId)

        val updatedUserPass = existingUser.copy(
            name = newPassword
        )

        try {

            val savedUserPass = userRepository.updatePassword(updatedUserPass)

            return Result.Success(savedUserPass)

        } catch (e: Exception) {
            return Result.Error("Не удалось обновить пароль: ${e.message}")
        }
    }
}