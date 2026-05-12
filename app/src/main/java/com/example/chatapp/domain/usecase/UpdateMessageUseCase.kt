package com.example.chatapp.domain.usecase

import com.example.chatapp.domain.model.Message
import com.example.chatapp.domain.repository.IMessage

class UpdateMessageUseCase(
    private val messageRepository: IMessage,
    private val newValue: String
) {

    sealed class Result {
        data class Success(val message: Message) : Result()
        data class Error(val message: String) : Result()
        data object ValidationError : Result()
    }

    operator fun invoke(params: Message): Result {

        if (params.value.isBlank()) {
            return Result.ValidationError
        }

        if (params.value.length > MAX_MESSAGE_LENGTH) {
            return Result.Error("Слишком длинное сообщение")
        }

        try {
            val message = Message(
                id = params.id,
                chatMemberId = params.chatMemberId,
                value = newValue,
                isUpdated = params.isUpdated,
                type = params.type,
                createdAt = params.createdAt,
                viewedAt = params.viewedAt,
                isDeleted = params.isDeleted,
                deletedAt = params.deletedAt
            )

            val savedMessage = messageRepository.save(message)

            return Result.Success(savedMessage)
        } catch (e: Exception) {
            return Result.Error("Не удалось изменить сообщение: ${e.message}")
        }
    }

    companion object {
        const val MAX_MESSAGE_LENGTH = 1000
    }
}