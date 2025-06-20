package com.example.chatapp.domain.usecase

import com.example.chatapp.domain.repository.IUser

class DeleteUserUseCase(private val userRepository: IUser) {
    operator fun invoke(userId: Int): Result<Boolean> {
        return try {
            val isDeleted = userRepository.delete(userId)
            if (isDeleted) {
                Result.success(true)
            } else {
                Result.failure(Exception("Удаление не удалось"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}