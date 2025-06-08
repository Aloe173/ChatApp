package com.example.chatapp.data.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "accounts",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["id_user"]
        )
    ]
)
data class Account(
    @PrimaryKey
    val id: Int,
    @ColumnInfo(name = "id_user")
    val idUser: Int,
    @ColumnInfo
    val token: String
)
