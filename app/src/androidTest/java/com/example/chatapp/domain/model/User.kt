package com.example.chatapp.domain.model

data class User(
    val id: Int,
    val name: String,
    val login: String,
    val isAdmin: Boolean,
    val passwordHash: String,
    val isDeleted: Boolean
)
