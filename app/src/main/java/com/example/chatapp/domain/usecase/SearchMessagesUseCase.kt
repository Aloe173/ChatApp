package com.example.chatapp.domain.usecase

import com.example.chatapp.domain.model.Message
import com.example.chatapp.domain.repository.IMessage

class SearchMessagesUseCase(
    private val messageRepository: IMessage
) {
    operator fun invoke(query: String): Result<List<Message>> {
        return try {
            val results = messageRepository.search(query)
            Result.success(results)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}