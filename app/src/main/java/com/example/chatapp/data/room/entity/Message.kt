package com.example.chatapp.data.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "messages",
    foreignKeys = [
        ForeignKey(
            entity = ChatMember::class,
            parentColumns = ["id"],
            childColumns = ["id_chat_member"]
        )
    ]
)
data class Message (
    @PrimaryKey
    val id: Int,
    @ColumnInfo(name = "id_chat_member")
    val idChatMember: Int,
    @ColumnInfo
    val value: String,
    @ColumnInfo
    val type: String,
    @ColumnInfo(name = "created_at")
    val createdAt: String,
    @ColumnInfo(name = "viewed_at")
    val viewedAt: String,
    @ColumnInfo(name = "deleted_at")
    val deletedAt: String,
    @ColumnInfo
    val deleted: Boolean
)