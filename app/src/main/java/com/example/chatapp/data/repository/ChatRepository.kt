package com.example.chatapp.data.repository

import com.example.chatapp.domain.enums.MessageType
import com.example.chatapp.domain.model.User
import com.example.chatapp.domain.model.Chat
import com.example.chatapp.domain.model.Message
import com.example.chatapp.domain.enums.ChatType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

object ChatRepository {
    private val currentUser = User(
        id = 0,
        name = "Я",
        login = "me"
    )

    private val users = listOf(
        User(1, "Анна", "anna"),
        User(2, "Петр", "petr"),
        User(3, "Мария", "masha"),
        User(4, "Иван", "ivan")
    )

    private val messages = listOf(
        Message(1, 1, users[0], "Привет! Как дела?", MessageType.TEXT, 1710000000000, false),
        Message(2, 1, currentUser, "Отлично! А у тебя?", MessageType.TEXT, 1710000060000, true),
        Message(3, 1, users[0], "Тоже хорошо", MessageType.TEXT, 1710000120000, false),
        Message(4, 2, users[1], "Всем привет!", MessageType.TEXT, 1710000180000, false),
        Message(5, 2, users[2], "Привет!", MessageType.TEXT, 1710000240000, false),
        Message(6, 2, currentUser, "Салют", MessageType.TEXT, 1710000300000, true),
        Message(7, 2, users[3], "И вам не хворать", MessageType.TEXT, 1710000360000, false),
    )

    private val chats = listOf(
        Chat(
            id = 1,
            name = "Анна",
            type = ChatType.PRIVATE,
            participants = listOf(users[0]),
            lastMessage = messages.lastOrNull { it.chatId == 1 },
            unreadCount = 2
        ),
        Chat(
            id = 2,
            name = "Рабочая группа",
            type = ChatType.GROUP,
            participants = users.subList(1, 4),
            lastMessage = messages.lastOrNull { it.chatId == 2 },
            unreadCount = 0
        )
    )

    fun getChats(): Flow<List<Chat>> = flow { emit(chats) }

    fun getMessages(chatId: Int): Flow<List<Message>> = flow {
        emit(messages.filter { it.chatId == chatId })
    }

    fun getCurrentUser(): User = currentUser
}