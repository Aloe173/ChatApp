package com.example.chatapp.domain.repository

import com.example.chatapp.domain.model.Right
import com.example.chatapp.domain.model.Role

interface IRole {
    fun create(role: Role): Role

    fun delete(roleId: Int): Boolean

    fun update(role: Role): Role?

    fun addRight(roleId: Int, right: Right): Role?

    fun removeRight(roleId: Int, right: Right): Role?
}