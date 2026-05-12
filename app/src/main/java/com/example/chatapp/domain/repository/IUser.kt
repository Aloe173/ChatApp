package com.example.chatapp.domain.repository

import android.graphics.Picture
import com.example.chatapp.domain.model.User

interface IUser {
    fun create(user: User): User

    fun delete(user: User): User

    fun updateName(user: User): User

    fun updatePassword(user: User): User

    fun updatePic(userId: Int, newPic: Picture): Boolean

    fun getUser(userId: Int): User

    fun getUser()
}