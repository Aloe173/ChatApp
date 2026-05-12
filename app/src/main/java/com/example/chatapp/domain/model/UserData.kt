package com.example.chatapp.domain.model

data class UserCredentials(
    val login: String = "",
    val password: String = ""
)

data class RegistrationData(
    val login: String = "",
    val password: String = "",
    val nickname: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val phoneNumber: String = "",
    val email: String = ""
)

data class ServerConfig(
    val ipAddress: String = ""
)