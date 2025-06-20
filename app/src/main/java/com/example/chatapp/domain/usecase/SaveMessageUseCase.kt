package com.example.chatapp.domain.usecase

import com.example.chatapp.domain.model.Message
import com.example.chatapp.domain.repository.IMessage

class SaveMessageUseCase(
    private val messageRepository: IMessage
) {
    operator fun invoke(message: Message): Result<Message> {
        return try {
            val savedMessage = messageRepository.save(message)
            Result.success(savedMessage)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}