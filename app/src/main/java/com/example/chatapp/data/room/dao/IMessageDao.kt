package com.example.chatapp.data.room.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.chatapp.data.room.entity.ChatMember
import com.example.chatapp.data.room.entity.Message

interface IMessageDao {
    @Insert
    suspend fun insert(message: Message)

    @Update
    suspend fun update(message: Message)

    @Delete
    suspend fun delete(message: Message)

    @Query("SELECT * FROM messages WHERE id_chat_member = :idChatMember")
    suspend fun getAllMessageByChatMemberId(idChatMember: Int): List<Message>

    @Query("SELECT * FROM messages WHERE value LIKE '%' || :value || '%' ")
    suspend fun getMessagesByValue(value: String): List<Message>
}