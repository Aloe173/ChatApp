package com.example.chatapp.data.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.chatapp.data.room.entity.Right

@Dao
interface IRightDao {

    @Insert
    suspend fun insert(right: Right)

    @Update
    suspend fun update(right: Right)

    @Delete
    suspend fun delete(right: Right)

    @Query("SELECT * FROM rights")
    suspend fun getAllRights(): List<Right>

    @Query("SELECT * FROM rights WHERE id_role = :roleId")
    suspend fun getRightsByRole(roleId: Int): List<Right>
}