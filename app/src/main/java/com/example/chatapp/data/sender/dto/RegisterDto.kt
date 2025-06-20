package com.example.chatapp.data.sender.dto

import kotlinx.serialization.Serializable

@Serializable
data class RegisterDto(
    val username: String,
    val login: String,
    val password: String
)
