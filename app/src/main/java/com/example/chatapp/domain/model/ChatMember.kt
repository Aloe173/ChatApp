package com.example.chatapp.domain.model

data class ChatMember(
    val id: Int,
    val isAdmin: Boolean,
    val chatId: Int,
    val roleId: Int,
    val userId: Int,
    val createdAt: String,
    val deletedAt: String,
    val deleted: Boolean
)
