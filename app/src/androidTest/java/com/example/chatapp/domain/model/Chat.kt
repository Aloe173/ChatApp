package com.example.chatapp.domain.model

data class Chat(
    val id: Int,
    val name: String,
    val owner: Int,
    val deleted: Boolean,
    val createdAt: String,
)
