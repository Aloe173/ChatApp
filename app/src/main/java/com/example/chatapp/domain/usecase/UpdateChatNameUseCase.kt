package com.example.chatapp.domain.usecase

import com.example.chatapp.domain.model.Chat
import com.example.chatapp.domain.repository.IChatRepository

class UpdateChatNameUseCase(
    private val chatRepository: IChatRepository,
    private val newName: String
) {

    sealed class Result {
        data class Success(val chat: Chat) : Result()
        data class Error(val message: String) : Result()
    }

    operator fun invoke(params: Chat): Result {

        try {
            val chat = Chat(
                id = params.id,
                name = newName,
                type = params.type,
                participants = params.participants,
                lastMessage = params.lastMessage,
                unreadCount = params.unreadCount,
            )

            val updatedChatName = chatRepository.update(chat)

            return Result.Success(updatedChatName)
        } catch (e: Exception) {
            return Result.Error("Не удалось обновить название чата: ${e.message}")
        }
    }
}