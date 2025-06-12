package com.example.chatapp.data.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "permissions")
data class Permission (
    @PrimaryKey
    val id: Int,
    @ColumnInfo
    val name: String,
    @ColumnInfo
    val deleted: Boolean
)