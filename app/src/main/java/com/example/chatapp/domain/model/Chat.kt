package com.example.chatapp.domain.model

data class Chat(
    val id: Int?,
    val name: String,
    val ownerId: Int,
    val createdAt: String?,
    val isDeleted: Boolean?
)
