package com.example.chatapp.domain.usecase

import com.example.chatapp.domain.model.Message
import com.example.chatapp.domain.repository.IMessage

class UpdateMessageUseCase(
    private val messageRepository: IMessage
) {
    operator fun invoke(message: Message): Result<Message> {
        return try {
            val updatedMessage = messageRepository.update(message)
            if (updatedMessage != null) {
                Result.success(updatedMessage)
            } else {
                Result.failure(RuntimeException("Сообщение не найдено"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}