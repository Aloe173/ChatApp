package com.example.chatapp.domain.repository

import com.example.chatapp.domain.model.ChatMember

interface IChatMember {
    fun add(member: ChatMember): ChatMember?

    fun remove(userId: Int, chatId: Int): Boolean

    fun updateRole(userId: Int, chatId: Int, roleId: Int?): ChatMember?
}