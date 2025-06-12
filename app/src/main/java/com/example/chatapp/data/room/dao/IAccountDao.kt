package com.example.chatapp.data.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.chatapp.data.room.entity.Account

@Dao
interface IAccountDao {
    @Insert
    suspend fun insert(account: Account)

    @Update
    suspend fun update(account: Account)

    @Delete
    suspend fun delete(account: Account)

    @Query("SELECT * FROM accounts WHERE active = 1")
    suspend fun getActiveAccount(): Account
}