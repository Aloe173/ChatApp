package com.example.chatapp.data.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.chatapp.data.room.entity.Role

@Dao
interface IRoleDao {

    @Insert
    suspend fun insert(role: Role)

    @Update
    suspend fun update(role: Role)

    @Delete
    suspend fun delete(role: Role)

    @Query("SELECT * FROM roles")
    suspend fun getAllRoles(): List<Role>
}