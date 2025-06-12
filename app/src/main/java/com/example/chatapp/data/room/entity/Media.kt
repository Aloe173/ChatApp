package com.example.chatapp.data.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "media",
    foreignKeys = [
        ForeignKey(
            entity = Message::class,
            parentColumns = ["id"],
            childColumns = ["id_message"]
        )
    ]
)
data class Media(
    @PrimaryKey
    val id: Int,
    @ColumnInfo(name = "id_message")
    val idMessage: Int,
    @ColumnInfo
    val type: String,
    @ColumnInfo
    val deleted: Boolean
)
