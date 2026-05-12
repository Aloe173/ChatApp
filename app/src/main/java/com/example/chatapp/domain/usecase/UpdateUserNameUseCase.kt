package com.example.chatapp.domain.usecase

import com.example.chatapp.domain.model.User
import com.example.chatapp.domain.repository.IUser

class UpdateUserNameUseCase(
    private val userRepository: IUser,
    private val newName: String
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

        val updatedUserName = existingUser.copy(
            name = newName
        )

        try {

            val savedUserName = userRepository.updateName(updatedUserName)

            return Result.Success(savedUserName)

        } catch (e: Exception) {
            return Result.Error("Не удалось обновить имя: ${e.message}")
        }
    }
}