package com.example.chatapp.presentation.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import java.text.SimpleDateFormat
import java.util.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.chatapp.domain.enums.ChatType
import com.example.chatapp.domain.model.Chat
import com.example.chatapp.domain.model.Message
import com.example.chatapp.presentation.viewmodel.ChatViewModel
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import com.example.chatapp.domain.model.User
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings

class ChatActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    ChatApp()
                }
            }
        }
    }
}

@Composable
fun ChatApp() {
    val navController = rememberNavController()
    val viewModel: ChatViewModel = viewModel()

    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentRoute in listOf("chat_list", "calls", "profile", "settings")) {
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = MaterialTheme.colorScheme.onSurface
                ) {
                    val items = listOf(
                        BottomNavItem("calls", "Звонки", Icons.Default.Call),
                        BottomNavItem("chat_list", "Чаты", Icons.Default.Email),
                        BottomNavItem("profile", "Профиль", Icons.Default.Person),
                        BottomNavItem("settings", "Настройки", Icons.Default.Settings)
                    )
                    items.forEach { item ->
                        NavigationBarItem(
                            selected = currentRoute == item.route,
                            onClick = {
                                navController.navigate(item.route) {
                                    popUpTo(navController.graph.startDestinationId) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = { Icon(item.icon, contentDescription = "") }
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = "chat_list",
            modifier = Modifier.padding(paddingValues) // важный момент – передаём отступы
        ) {
            composable("chat_list") {
                ChatListScreen(
                    viewModel = viewModel,
                    onChatClick = { chat ->
                        viewModel.selectChat(chat)
                        navController.navigate("chat_detail")
                    },
                    onCreateNewChat = {
                        navController.navigate("create_chat")
                    }
                )
            }
            composable("chat_detail") {
                ChatDetailScreen(
                    viewModel = viewModel,
                    onBackClick = {
                        viewModel.clearSelectedChat()
                        navController.popBackStack()
                    }
                )
            }
            composable("create_chat") {
                CreateChatScreen(
                    viewModel = viewModel,
                    onNavigateBack = { navController.popBackStack() },
                    onChatCreated = { chat ->
                        viewModel.selectChat(chat)
                        navController.popBackStack()
                        navController.navigate("chat_detail")
                    }
                )
            }
            composable("calls") { CallScreen() }
            composable("profile") { ProfileScreen() }
            composable("settings") { SettingsScreen() }
        }
    }
}

@Composable
fun CallScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Экран звонков", style = MaterialTheme.typography.headlineSmall)
    }
}

@Composable
fun ProfileScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Профиль", style = MaterialTheme.typography.headlineSmall)
    }
}

@Composable
fun SettingsScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Настройки", style = MaterialTheme.typography.headlineSmall)
    }
}

@Composable
fun ChatListScreen(
    viewModel: ChatViewModel,
    onChatClick: (Chat) -> Unit,
    onCreateNewChat: () -> Unit
) {
    val chats by viewModel.chats.collectAsStateWithLifecycle()
    var searchQuery by remember { mutableStateOf("") }

    // Фильтр чатов
    val filteredChats = remember(searchQuery, chats) {
        if (searchQuery.isBlank()) {
            chats
        } else {
            chats.filter { chat ->
                chat.name.contains(searchQuery, ignoreCase = true)
            }
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onCreateNewChat) {
                Icon(Icons.Default.Add, contentDescription = "Новый чат")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Поле поиска
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Поиск") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                singleLine = true,
                shape = RoundedCornerShape(24.dp)
            )

            if (filteredChats.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (searchQuery.isBlank()) "Нет чатов" else "Ничего не найдено",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else {
                LazyColumn {
                    items(filteredChats) { chat ->
                        ChatItem(
                            chat = chat,
                            onClick = { onChatClick(chat) }
                        )
                        HorizontalDivider()
                    }
                }
            }
        }
    }
}

@Composable
fun ChatItem(chat: Chat, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Аватар
        Box(
            modifier = Modifier
                .size(48.dp)
                .padding(end = 12.dp)
        ) {
            Surface(
                shape = MaterialTheme.shapes.small,
                color = MaterialTheme.colorScheme.primaryContainer
            ) {
                Text(
                    text = chat.name.first().toString(),
                    modifier = Modifier.padding(8.dp)
                )
            }
        }

        Column(modifier = Modifier.weight(1f)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = chat.name,
                    style = MaterialTheme.typography.titleMedium
                )
                chat.lastMessage?.let {
                    Text(
                        text = formatTime(it.createdAt),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = chat.lastMessage?.value ?: "Нет сообщений",
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1,
                    modifier = Modifier.weight(1f)
                )
                if (chat.unreadCount > 0) {
                    Badge(
                        modifier = Modifier.padding(start = 8.dp)
                    ) {
                        Text(text = chat.unreadCount.toString())
                    }
                }
            }
        }
    }
}

private fun formatTime(timestamp: Long): String {
    val date = Date(timestamp)
    val format = SimpleDateFormat("HH:mm", Locale.getDefault())
    return format.format(date)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatDetailScreen(
    viewModel: ChatViewModel,
    onBackClick: () -> Unit
) {
    val currentUser by remember { mutableStateOf(viewModel.getCurrentUser()) }
    val messages by viewModel.messages.collectAsStateWithLifecycle()
    val chat by viewModel.currentChat.collectAsStateWithLifecycle()
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    // Прокрутка вниз при новых сообщениях
    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.lastIndex)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(chat?.name ?: "Чат") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Назад")
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 8.dp),
            state = listState,
            reverseLayout = false,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(messages) { message ->
                MessageItem(
                    message = message,
                    isCurrentUser = message.sender.id == currentUser.id,
                    showSenderName = chat?.type == ChatType.GROUP && message.sender.id != currentUser.id
                )
            }
        }
    }
}

@Composable
fun MessageItem(
    message: Message,
    isCurrentUser: Boolean,
    showSenderName: Boolean
) {
    val alignment = if (isCurrentUser) Alignment.End else Alignment.Start
    val backgroundColor = if (isCurrentUser)
        MaterialTheme.colorScheme.primaryContainer
    else
        MaterialTheme.colorScheme.secondaryContainer

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = alignment
    ) {
        if (showSenderName) {
            Text(
                text = message.sender.name,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(start = 8.dp, bottom = 2.dp)
            )
        }
        Card(
            modifier = Modifier
                .widthIn(max = 280.dp)
                .padding(4.dp),
            shape = RoundedCornerShape(
                topStart = 16.dp,
                topEnd = 16.dp,
                bottomStart = if (isCurrentUser) 16.dp else 4.dp,
                bottomEnd = if (isCurrentUser) 4.dp else 16.dp
            ),
            colors = CardDefaults.cardColors(containerColor = backgroundColor)
        ) {
            Column(
                modifier = Modifier.padding(12.dp)
            ) {
                Text(
                    text = message.value,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = formatTime(message.createdAt),
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.align(Alignment.End)
                )
            }
        }
    }
}

@SuppressLint("RememberReturnType")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateChatScreen(
    viewModel: ChatViewModel,
    onNavigateBack: () -> Unit,
    onChatCreated: (Chat) -> Unit
) {
    val allUsers by viewModel.allUsers.collectAsState()
    val selectedUsers by viewModel.selectedUsers.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val filteredUsers by viewModel.filteredUsers.collectAsState()

    val focusRequester = remember { FocusRequester() }
    var isFocused by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.resetCreateChatState()
        focusRequester.requestFocus()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Новый чат") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Назад")
                    }
                },
                actions = {
                    TextButton(
                        onClick = {
                            val chat = viewModel.createChat()
                            if (chat != null) {
                                onChatCreated(chat)
                            }
                        },
                        enabled = selectedUsers.isNotEmpty()
                    ) {
                        Text("Создать")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // Поле поиска
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { viewModel.updateSearchQuery(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester)
                    .onFocusChanged { isFocused = it.isFocused },
                label = { Text("Поиск участников") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                singleLine = true,
                shape = RoundedCornerShape(24.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Список выбранных пользователей
            if (selectedUsers.isNotEmpty()) {
                Text(
                    text = "Выбранные участники:",
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(bottom = 16.dp)
                ) {
                    items(selectedUsers) { user ->
                        AssistChip(
                            onClick = { viewModel.deselectUser(user) },
                            label = { Text(user.name) },
                            trailingIcon = {
                                Icon(
                                    Icons.Default.Close,
                                    contentDescription = "Удалить",
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        )
                    }
                }
            }

            // Список пользователей (результаты поиска)
            if (isFocused || searchQuery.isNotBlank()) {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    items(filteredUsers) { user ->
                        UserItem(
                            user = user,
                            onClick = { viewModel.selectUser(user) }
                        )
                    }

                    if (filteredUsers.isEmpty() && allUsers.isNotEmpty()) {
                        item {
                            Text(
                                "Пользователи не найдены",
                                modifier = Modifier.padding(16.dp),
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            } else {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "Начните вводить имя для поиска",
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
fun UserItem(
    user: User,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Аватар
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primaryContainer),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = user.name.first().toString(),
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = user.name,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ChatActivityPreview() {
    MaterialTheme {
        val navController = rememberNavController()
        val viewModel = ChatViewModel() // экземпляр для превью

        val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

        Scaffold(
            bottomBar = {
                if (currentRoute in listOf("chat_list", "calls", "profile", "settings")) {
                    NavigationBar(
                        containerColor = MaterialTheme.colorScheme.surface,
                        contentColor = MaterialTheme.colorScheme.onSurface
                    ) {
                        val items = listOf(
                            BottomNavItem("calls", "Звонки", Icons.Default.Call),
                            BottomNavItem("chat_list", "Чаты", Icons.Default.Email),
                            BottomNavItem("profile", "Профиль", Icons.Default.Person),
                            BottomNavItem("settings", "Настройки", Icons.Default.Settings)
                        )
                        items.forEach { item ->
                            NavigationBarItem(
                                selected = currentRoute == item.route,
                                onClick = {
                                    navController.navigate(item.route) {
                                        popUpTo(navController.graph.startDestinationId) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                },
                                icon = { Icon(item.icon, contentDescription = null) }
                            )
                        }
                    }
                }
            }
        ) { paddingValues ->
            NavHost(
                navController = navController,
                startDestination = "chat_list",
                modifier = Modifier.padding(paddingValues)
            ) {
                composable("chat_list") {
                    ChatListScreen(
                        viewModel = viewModel,
                        onChatClick = { chat ->
                            viewModel.selectChat(chat)
                            navController.navigate("chat_detail")
                        },
                        onCreateNewChat = {
                            navController.navigate("create_chat")
                        }
                    )
                }
                composable("chat_detail") {
                    ChatDetailScreen(
                        viewModel = viewModel,
                        onBackClick = {
                            viewModel.clearSelectedChat()
                            navController.popBackStack()
                        }
                    )
                }
                composable("create_chat") {
                    CreateChatScreen(
                        viewModel = viewModel,
                        onNavigateBack = { navController.popBackStack() },
                        onChatCreated = { chat ->
                            viewModel.selectChat(chat)
                            navController.popBackStack()
                            navController.navigate("chat_detail")
                        }
                    )
                }
                composable("calls") { CallScreen() }
                composable("profile") { ProfileScreen() }
                composable("settings") { SettingsScreen() }
            }
        }
    }
}