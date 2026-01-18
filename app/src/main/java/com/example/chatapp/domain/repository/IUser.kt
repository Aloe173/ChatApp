package com.example.chatapp.domain.repository

import android.graphics.Picture
import com.example.chatapp.domain.model.User

interface IUser {
    fun create(user: User): User

    fun getNextId(): Int

    fun delete(user: User): User

    fun updateName(userId: Int, newName: String): User

    fun updatePassword(userId: Int, newPassword: String): Boolean

    fun updatePic(userId: Int, newPic: Picture): Boolean

    fun getUserById(userId: Int): Any
}