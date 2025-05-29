package com.example.chatapp.domain.repository

import com.example.chatapp.domain.model.Message

interface IMessage {
    fun save(message: Message): Message

    fun delete(messageId: Int): Boolean

    fun update(message: Message): Message?

    fun search(query: String): List<Message>
}