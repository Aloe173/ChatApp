package com.example.chatapp.domain.usecase

import com.example.chatapp.domain.model.ChatMember
import com.example.chatapp.domain.repository.IChatMember

class CreateChatMemberUseCase(
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
                isDeleted = false
            )

            val createdMember = memberRepository.create(member)

            return Result.Success(createdMember)
        } catch (e: Exception) {
            return Result.Error("Не удалось создать участника чата: ${e.message}")
        }
    }
}