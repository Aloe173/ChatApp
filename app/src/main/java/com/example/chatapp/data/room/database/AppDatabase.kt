package com.example.chatapp.data.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.chatapp.data.room.dao.UserDao
import com.example.chatapp.data.room.entity.User

@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}