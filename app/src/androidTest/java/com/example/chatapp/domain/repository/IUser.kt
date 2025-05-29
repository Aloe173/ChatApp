package com.example.chatapp.domain.repository

import android.graphics.Picture
import com.example.chatapp.domain.model.User

interface IUser {
    fun create(user: User): User

    fun delete(userId: Int): Boolean

    fun updateName(userId: Int, newName: String): Boolean

    fun updatePassword(userId: Int, newPassword: String): Boolean

    fun updatePic(userId: Int, newPic: Picture): Boolean
}