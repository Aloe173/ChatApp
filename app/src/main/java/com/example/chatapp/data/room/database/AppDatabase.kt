package com.example.chatapp.data.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.chatapp.data.room.dao.IAccountDao
import com.example.chatapp.data.room.dao.IChatDao
import com.example.chatapp.data.room.dao.IChatMemberDao
import com.example.chatapp.data.room.dao.IMediaDao
import com.example.chatapp.data.room.dao.IMessageDao
import com.example.chatapp.data.room.dao.IPermissionDao
import com.example.chatapp.data.room.dao.IRightDao
import com.example.chatapp.data.room.dao.IRoleDao
import com.example.chatapp.data.room.dao.IUserDao
import com.example.chatapp.data.room.entity.Account
import com.example.chatapp.data.room.entity.Chat
import com.example.chatapp.data.room.entity.ChatMember
import com.example.chatapp.data.room.entity.Media
import com.example.chatapp.data.room.entity.Message
import com.example.chatapp.data.room.entity.Permission
import com.example.chatapp.data.room.entity.Right
import com.example.chatapp.data.room.entity.Role
import com.example.chatapp.data.room.entity.User

@Database(
    entities = [
        User::class,
        Role::class,
        Right::class,
        Permission::class,
        Message::class,
        Media::class,
        ChatMember::class,
        Chat::class,
        Account::class
    ],
    version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): IUserDao
    abstract fun roleDao(): IRoleDao
    abstract fun rightDao(): IRightDao
    abstract fun permissionDao(): IPermissionDao
    abstract fun messageDao():IMessageDao
    abstract fun mediaDao(): IMediaDao
    abstract fun chatMemberDao(): IChatMemberDao
    abstract fun chatDao(): IChatDao
    abstract fun accountDao(): IAccountDao
}