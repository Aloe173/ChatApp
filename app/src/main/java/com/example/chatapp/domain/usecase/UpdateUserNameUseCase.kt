package com.example.chatapp.domain.usecase

import androidx.room.util.copy
import com.example.chatapp.domain.model.User
import com.example.chatapp.domain.repository.IUser

class UpdateUserNameUseCase(
    private val userRepository: IUser
) {

    data class Params(
        val userId: Int,
        val newName: String
    )

    sealed class Result {
        data class Success(val user: User) : Result()
        data class Error(val message: String) : Result()
        data object UserNotFound : Result()
        data object NoChangesDetected : Result()
    }

    suspend operator fun invoke(params: Params): Result {

        val existingUser = userRepository.getUserById(params.userId)
        if (existingUser == null) {
            return Result.UserNotFound
        }

        if (existingUser.isDeleted) {
            return Result.Error("Cannot update deleted user")
        }

        if (existingUser.name == params.newName) {
            return Result.NoChangesDetected
        }


        val updatedUser = existingUser.copy(
            name = params.newName
        )

        try {

            val savedUser = userRepository.updateName(updatedUser)

            return Result.Success(savedUser)

        } catch (e: Exception) {
            return Result.Error("Failed to update user name: ${e.message}")
        }
    }
}