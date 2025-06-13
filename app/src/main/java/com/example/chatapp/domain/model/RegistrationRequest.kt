package com.example.chatapp.domain.model

data class RegistrationRequest(
    val name: String,
    val username: String,
    val password: String
)