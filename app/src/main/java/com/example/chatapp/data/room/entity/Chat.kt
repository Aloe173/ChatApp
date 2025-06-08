package com.example.chatapp.data.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "chats",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["owner"]
        )
    ]
)
data class Chat (
    @PrimaryKey
    val id: Int,
    @ColumnInfo
    val owner: Int,
    @ColumnInfo
    val name: String,
    @ColumnInfo(name = "created_at")
    val createdAt: String
)