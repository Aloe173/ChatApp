package com.example.chatapp.domain.usecase

import com.example.chatapp.domain.model.User
import com.example.chatapp.domain.repository.IUser

class DeleteUserUseCase(
    private val userRepository: IUser)
    {

    sealed class Result {
        data class Success(val user: User) : Result()
        data class Error(val message: String) : Result()
    }

    operator fun invoke(params: User): Result {

        try {
            val user = User(
                id = params.id,
                name = params.name,
                login = params.login
            )

            val deletedUser = userRepository.delete(user)

            return Result.Success(deletedUser)
        } catch (e: Exception) {
            return Result.Error("Не удалось удалить пользователя: ${e.message}")
        }
    }
}