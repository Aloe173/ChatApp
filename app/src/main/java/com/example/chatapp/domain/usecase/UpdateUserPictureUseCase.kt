package com.example.chatapp.domain.usecase

import android.graphics.Picture
import com.example.chatapp.domain.repository.IUser

class UpdateUserPictureUseCase(private val userRepository: IUser) {
     operator fun invoke(userId: Int, newPic: Picture): Result<Boolean> {
        return try {
            val isUpdated = userRepository.updatePic(userId, newPic)
            if (isUpdated) {
                Result.success(true)
            } else {
                Result.failure(Exception("Обновление аватара не удалось"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}