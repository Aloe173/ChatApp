package com.example.chatapp.domain.model

import com.example.chatapp.domain.enums.MessageType

data class Message(
    val id: Int?,
    val chatId: Int,
    val sender: User,
    val value: String,
    val type: MessageType,
    val createdAt: Long,
    val isCurrentUser: Boolean
    )
