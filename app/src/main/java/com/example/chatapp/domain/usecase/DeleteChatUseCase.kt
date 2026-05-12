package com.example.chatapp.domain.usecase

import com.example.chatapp.domain.model.Chat
import com.example.chatapp.domain.repository.IChatRepository

class DeleteChatUseCase(
    private val chatRepository: IChatRepository
) {

    sealed class Result {
        data class Success(val chat: Chat) : Result()
        data class Error(val message: String) : Result()
    }

    operator fun invoke(params: Chat): Result {

        try {
            val chat = Chat(
                id = params.id,
                name = params.name,
                type = params.type,
                participants = params.participants,
                lastMessage = params.lastMessage,
                unreadCount = params.unreadCount
            )

            val createdChat = chatRepository.create(chat)

            return Result.Success(createdChat)
        } catch (e: Exception) {
            return Result.Error("Не удалось удалить чат: ${e.message}")
        }
    }
}