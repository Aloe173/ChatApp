package com.example.chatapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatapp.data.repository.ChatRepository
import com.example.chatapp.domain.model.Chat
import com.example.chatapp.domain.enums.ChatType
import com.example.chatapp.domain.model.Message
import com.example.chatapp.domain.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.update

class ChatViewModel : ViewModel() {
    private val repository = ChatRepository

    private val _chats = MutableStateFlow<List<Chat>>(emptyList())
    val chats: StateFlow<List<Chat>> = _chats.asStateFlow()

    private val _messages = MutableStateFlow<List<Message>>(emptyList())
    val messages: StateFlow<List<Message>> = _messages.asStateFlow()

    private val _currentChat = MutableStateFlow<Chat?>(null)
    val currentChat: StateFlow<Chat?> = _currentChat.asStateFlow()

    private val _allUsers = MutableStateFlow<List<User>>(emptyList())
    val allUsers: StateFlow<List<User>> = _allUsers.asStateFlow()

    private val _selectedUsers = MutableStateFlow<List<User>>(emptyList())
    val selectedUsers: StateFlow<List<User>> = _selectedUsers.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    // Отфильтрованные пользователи (исключая выбранных)
    val filteredUsers: StateFlow<List<User>> = combine(
        allUsers, selectedUsers, searchQuery
    ) { users, selected, query ->
        users.filter { user ->
            user !in selected && user.name.contains(query, ignoreCase = true)
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        loadUsers()
    }

    private fun loadUsers() {
        viewModelScope.launch {
            // Загружаем из репозитория (здесь тестовые данные)
            _allUsers.value = listOf(
                User(1, "Анна", "anna"),
                User(2, "Петр", "petr"),
                User(3, "Мария", "masha"),
                User(4, "Иван", "ivan")
            )
        }
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun selectUser(user: User) {
        _selectedUsers.update { it + user }
    }

    fun deselectUser(user: User) {
        _selectedUsers.update { it - user }
    }

    fun resetCreateChatState() {
        _selectedUsers.value = emptyList()
        _searchQuery.value = ""
    }

    fun createChat(): Chat? {
        if (_selectedUsers.value.isEmpty()) return null

        val currentUser = getCurrentUser() // предположим, есть такая функция
        val chat = if (_selectedUsers.value.size == 1) {
            // Личный чат
            val otherUser = _selectedUsers.value.first()
            Chat(
                //id = generateChatId(),
                id = 3,
                name = otherUser.name,
                type = ChatType.PRIVATE,
                participants = listOf(currentUser, otherUser),
                lastMessage = null,
                unreadCount = 0
            )
        } else {
            // Групповой чат
            val groupName = "Группа ${_selectedUsers.value.joinToString { it.name }}"
            Chat(
                //id = generateChatId(),
                id = 4,
                name = groupName,
                type = ChatType.GROUP,
                participants = listOf(currentUser) + _selectedUsers.value,
                lastMessage = null,
                unreadCount = 0
            )
        }

        viewModelScope.launch {
            // Сохраняем чат в репозиторий
            // chatRepository.createChat(chat)
        }
        return chat
    }

    init {
        loadChats()
    }

    private fun loadChats() {
        viewModelScope.launch {
            repository.getChats().collect { list ->
                _chats.value = list
            }
        }
    }

    fun selectChat(chat: Chat) {
        _currentChat.value = chat
        loadMessages(chat.id)
    }

    private fun loadMessages(chatId: Int) {
        viewModelScope.launch {
            repository.getMessages(chatId).collect { list ->
                _messages.value = list
            }
        }
    }

    fun getCurrentUser(): User = repository.getCurrentUser()

    fun clearSelectedChat() {
        _currentChat.value = null
        _messages.value = emptyList()
    }
}
