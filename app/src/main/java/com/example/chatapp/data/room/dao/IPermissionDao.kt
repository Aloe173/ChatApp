package com.example.chatapp.data.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.chatapp.data.room.entity.Permission

@Dao
interface IPermissionDao {

    @Insert
    suspend fun insert(permission: Permission)

    @Update
    suspend fun update(permission: Permission)

    @Delete
    suspend fun delete(permission: Permission)

    @Query("SELECT * FROM permissions")
    suspend fun getAllPermissions(): List<Permission>
}