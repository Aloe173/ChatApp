package com.example.chatapp.domain.usecase

import com.example.chatapp.domain.repository.IMessage

class DeleteMessageUseCase(
    private val messageRepository: IMessage
) {
    operator fun invoke(messageId: Int): Result<Boolean> {
        return try {
            val isDeleted = messageRepository.delete(messageId)
            if (isDeleted) {
                Result.success(true)
            } else {
                Result.failure(RuntimeException("Сообщение не найдено"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}