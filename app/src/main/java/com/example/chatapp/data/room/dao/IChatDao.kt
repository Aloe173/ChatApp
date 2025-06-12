package com.example.chatapp.data.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.chatapp.data.room.entity.Chat

@Dao
interface IChatDao {

    @Insert
    suspend fun insert(chat: Chat)

    @Update
    suspend fun update(chat: Chat)

    @Delete
    suspend fun delete(chat: Chat)

    @Query("SELECT chats.* FROM chats " +
            "LEFT JOIN chat_members ON chat_members.id_chat = chats.id " +
            "WHERE chat_members.id_user = :idUser")
    suspend fun getAllChatsByIdUser(idUser: Int): List<Chat>

    @Query("SELECT * FROM chats WHERE id = :idChat")
    suspend fun getChatById(idChat: Int): Chat
}