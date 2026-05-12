package com.example.chatapp.domain.model

import com.example.chatapp.domain.enums.ChatType

data class Chat(
    val id: Int,
    val name: String,
    val type: ChatType,
    val participants: List<User>,
    val lastMessage: Message?,
    val unreadCount: Int = 0
)
