package com.example.chatapp.domain.usecase

import com.example.chatapp.domain.model.User
import com.example.chatapp.domain.repository.IUser

class CreateUserUseCase(private val userRepository: IUser) {
    operator fun invoke(user: User): Result<User> {
        return try {
            val createdUser = userRepository.create(user)
            Result.success(createdUser)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}