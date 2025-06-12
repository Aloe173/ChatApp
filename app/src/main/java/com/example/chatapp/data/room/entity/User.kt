package com.example.chatapp.data.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User (
    @PrimaryKey
    val id: Int,
    @ColumnInfo
    val name: String,
    @ColumnInfo
    val login: String,
    @ColumnInfo(name = "is_admin")
    val isAdmin: Boolean,
    @ColumnInfo
    val deleted: Boolean
)