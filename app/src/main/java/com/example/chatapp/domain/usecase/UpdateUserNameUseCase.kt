package com.example.chatapp.domain.usecase

import com.example.chatapp.domain.repository.IUser

class UpdateUserNameUseCase(private val userRepository: IUser) {
    operator fun invoke(userId: Int, newName: String): Result<Boolean> {
        return try {
            val isUpdated = userRepository.updateName(userId, newName)
            if (isUpdated) {
                Result.success(true)
            } else {
                Result.failure(Exception("Обновление имени не удалось"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}