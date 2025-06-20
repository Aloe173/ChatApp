package com.example.chatapp.domain.usecase

import com.example.chatapp.domain.repository.IUser

class UpdateUserPasswordUseCase(private val userRepository: IUser) {
    operator fun invoke(userId: Int, newPassword: String): Result<Boolean> {
        return try {
            val isUpdated = userRepository.updatePassword(userId, newPassword)
            if (isUpdated) {
                Result.success(true)
            } else {
                Result.failure(Exception("Обновление пароля не удалось"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}