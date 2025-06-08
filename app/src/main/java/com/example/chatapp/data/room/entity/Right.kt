package com.example.chatapp.data.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "rights",
    foreignKeys = [
        ForeignKey(
            entity = Role::class,
            parentColumns = ["id"],
            childColumns = ["id_role"]
        )
    ]
)
data class Right(
    @PrimaryKey
    val id: Int,
    @ColumnInfo(name = "id_role")
    val idRole: Int,
    @ColumnInfo
    val name: String,
    @ColumnInfo
    val deleted: Boolean
)
