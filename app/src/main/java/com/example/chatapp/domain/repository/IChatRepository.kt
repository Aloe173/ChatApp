package com.example.chatapp.domain.repository

import com.example.chatapp.domain.model.Chat

interface IChatRepository {
    fun create(chat: Chat): Chat

    fun delete(chatId: Int): Boolean
}


