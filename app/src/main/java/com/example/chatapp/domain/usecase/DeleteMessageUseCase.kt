package com.example.chatapp.domain.usecase

import com.example.chatapp.domain.model.Message
import com.example.chatapp.domain.repository.IMessage

class DeleteMessageUseCase(
    private val messageRepository: IMessage
) {

    sealed class Result {
        data class Success(val message: Message) : Result()
        data class Error(val message: String) : Result()
    }

    operator fun invoke(params: Message): Result {

        try {
            val message = Message(
                id = params.id,
                chatMemberId = params.chatMemberId,
                value = params.value,
                isUpdated = params.isUpdated,
                type = params.type,
                createdAt = params.createdAt,
                viewedAt = params.viewedAt,
                isDeleted = true,
                deletedAt = params.deletedAt
            )

            val deletedMessage = messageRepository.delete(message)

            return Result.Success(deletedMessage)
        } catch (e: Exception) {
            return Result.Error("Не удалось удалить сообщение: ${e.message}")
        }
    }
}