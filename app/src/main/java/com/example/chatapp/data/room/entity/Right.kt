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
        ),
        ForeignKey(
            entity = Permission::class,
            parentColumns = ["id"],
            childColumns = ["id_permission"]
        )
    ]
)
data class Right(
    @PrimaryKey
    val id: Int,
    @ColumnInfo(name = "id_role")
    val idRole: Int,
    @ColumnInfo(name = "id_permission")
    val idPermission: Int,
    @ColumnInfo
    val deleted: Boolean
)
