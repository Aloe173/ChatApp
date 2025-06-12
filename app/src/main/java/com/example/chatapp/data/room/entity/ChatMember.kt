package com.example.chatapp.data.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "chat_members",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["id_user"]
        ),
        ForeignKey(
            entity = Chat::class,
            parentColumns = ["id"],
            childColumns = ["id_chat"]
        ),
        ForeignKey(
            entity = Role::class,
            parentColumns = ["id"],
            childColumns = ["id_role"]
        ),
    ]
)
data class ChatMember (
    @PrimaryKey
    val id: Int,
    @ColumnInfo(name = "id_user")
    val idUser: Int,
    @ColumnInfo(name = "id_chat")
    val idChat: Int,
    @ColumnInfo(name = "id_role")
    val idRole: Int,
    @ColumnInfo(name = "created_at")
    val createdAt: String,
    @ColumnInfo(name = "deleted_at")
    val deletedAt: String,
    @ColumnInfo
    val deleted: Boolean
)