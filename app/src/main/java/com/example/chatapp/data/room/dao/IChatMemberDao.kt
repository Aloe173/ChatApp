package com.example.chatapp.data.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.chatapp.data.room.entity.ChatMember

@Dao
interface IChatMemberDao {

    @Insert
    suspend fun insert(chatMember: ChatMember)

    @Update
    suspend fun update(chatMember: ChatMember)

    @Delete
    suspend fun delete(chatMember: ChatMember)

    @Query("SELECT * FROM chat_members WHERE id_chat = :idChat")
    suspend fun getAllChatMemberByChat(idChat: Int): List<ChatMember>

    @Query("SELECT * FROM chat_members WHERE id = :userId")
    suspend fun getCurrentUser(userId: Int): ChatMember
}