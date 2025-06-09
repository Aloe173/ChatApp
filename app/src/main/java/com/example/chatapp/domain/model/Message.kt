package com.example.chatapp.domain.model

import com.example.chatapp.domain.enums.MessageType

data class Message(
    val id: Int,
    val chatMemberId: Int,
    val value: String,
    val type: MessageType,
    val createdAt: String,
    val viewedAt: String,
    val isDeleted: Boolean,
    val deletedAt: String
    )
