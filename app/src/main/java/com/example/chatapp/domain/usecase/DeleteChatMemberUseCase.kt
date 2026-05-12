package com.example.chatapp.domain.usecase

import com.example.chatapp.domain.model.ChatMember
import com.example.chatapp.domain.repository.IChatMember

class DeleteChatMemberUseCase(
    private val memberRepository: IChatMember
) {

    sealed class Result {
        data class Success(val member: ChatMember) : Result()
        data class Error(val message: String) : Result()
    }

    operator fun invoke(params: ChatMember): Result {

        try {
            val member = ChatMember(
                id = params.id,
                chatId = params.chatId,
                roleId = params.roleId,
                userId = params.userId,
                createdAt = params.createdAt,
                deletedAt = params.deletedAt,
                isDeleted = true
            )

            val deletedMember = memberRepository.delete(member)

            return Result.Success(deletedMember)
        } catch (e: Exception) {
            return Result.Error("Не удалось удалить участника чата: ${e.message}")
        }
    }
}