package com.example.chatapp.presentation.ui

import java.util.Locale
import android.os.Bundle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import com.example.chatapp.domain.enums.MessageType
import com.example.chatapp.domain.model.Chat
import com.example.chatapp.domain.model.Message
import java.text.SimpleDateFormat
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.navigation.compose.rememberNavController
import androidx.compose.foundation.layout.absolutePadding
import com.example.chatapp.ui.theme.BottomNavigationTheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.material.icons.automirrored.filled.ArrowBack

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BottomNavigationTheme {
                val navController = rememberNavController()
                Scaffold(
                    bottomBar = {
                        BottomNavigationBar(
                            items = listOf(
                                BottomNavItem(
                                    name = "Chat",
                                    route = "chat",
                                    icon = Icons.Default.Add
                                ),
                                BottomNavItem(
                                    name = "Home",
                                    route = "home",
                                    icon = Icons.Default.Notifications,
                                    badgeCount = 0
                                ),
                                BottomNavItem(
                                    name = "Profile",
                                    route = "profile",
                                    icon = Icons.Default.AccountCircle,
                                ),
                            ),
                            navController = navController,
                            onItemClick = { item ->
                                navController.navigate(item.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
                ) { innerPadding ->
                    Navigation(
                        navController = navController,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Navigation(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = "home",
        modifier = modifier
    ) {
        composable("home") {
            HomeScreen(
//                onChatClick = { chatId ->
//                    navController.navigate("chat/$chatId")
//                }
            )
        }
//        composable("chat/{chatId}") { backStackEntry ->
//            val chatId = backStackEntry.arguments?.getString("chatId") ?: ""
//            ChatScreen(
//                chatId = chatId,
//                onBackClick = { navController.popBackStack() }
//            )
//        }
        composable("chat") {
            NewChatScreen()
        }
        composable("profile") {
            ProfileScreen()
        }
    }
}

@Composable
fun BottomNavigationBar(
    items: List<BottomNavItem>,
    navController: NavHostController,
    modifier: Modifier = Modifier,
    onItemClick: (BottomNavItem) -> Unit
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
        tonalElevation = 5.dp
    ) {
        items.forEach { item ->
            val selected = currentDestination?.route == item.route
            NavigationBarItem(
                selected = selected,
                onClick = { onItemClick(item) },
                icon = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        if (item.badgeCount > 0) {
                            BadgedBox(
                                badge = {
                                    Text(text = item.badgeCount.toString())
                                }
                            ) {
                                Icon(
                                    imageVector = item.icon,
                                    contentDescription = item.name
                                )
                            }
                        } else {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.name
                            )
                        }
                    }
                },
                label = {
                    if (selected) {
                        Text(
                            text = item.name,
                            fontSize = 10.sp
                        )
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    indicatorColor = MaterialTheme.colorScheme.surfaceVariant
                )
            )
        }
    }
}

@Composable
fun HomeScreen(
//    onChatClick: (String) -> Unit,
    chats: List<Pair<Chat, Message?>> = emptyList()
) {
    var searchQuery by remember { mutableStateOf("") }
    val filteredChats = remember(searchQuery, chats) {
        if (searchQuery.isBlank()) chats
        else chats.filter { (chat, _) ->
            chat.name.contains(searchQuery, ignoreCase = true)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE8EEF1))
    ) {
        Text(
            modifier = Modifier.padding(20.dp),
            text = "Сообщения",
            fontSize = 24.sp,
            fontFamily = fontFamily,
            fontWeight = FontWeight.Bold
        )

        SearchBar(
            query = searchQuery,
            onQueryChange = { searchQuery = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 16.dp)
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(filteredChats) { (chat, lastMessage) ->
                ChatItem(
                    chat = chat,
                    lastMessage = lastMessage,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            if (lastMessage?.viewedAt == null) Color(0xFFD6E0E8) else Color.White
                        )
//                        .clickable { onChatClick(chat.id.toString()) }
                        .padding(16.dp)
                )

                HorizontalDivider(
                    color = Color.LightGray,
                    thickness = 1.dp,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        }
    }
}

@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = modifier,
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Поиск"
            )
        },
        placeholder = {
            Text("Поиск чатов...")
        },
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.White,
            focusedContainerColor = Color.White,
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent
        ),
        shape = RoundedCornerShape(16.dp),
        singleLine = true
    )
}

//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun ChatScreen(
//    chatId: String,
//    onBackClick: () -> Unit,
//    viewModel: ChatViewModel = viewModel()
//) {
//    val chat by viewModel.chat.collectAsState()
//    val messages by viewModel.messages.collectAsState()
//
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = { Text(chat?.name ?: "Чат") },
//                navigationIcon = {
//                    IconButton(onClick = onBackClick) {
//                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Назад")
//                    }
//                }
//            )
//        }
//    ) { innerPadding ->
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(innerPadding)
//                .background(Color(0xFFE8EEF1))
//        ) {
//
//            LazyColumn(
//                modifier = Modifier
//                    .weight(1f)
//                    .padding(horizontal = 16.dp),
//                reverseLayout = true
//            ) {
//                items(messages) { message ->
//                    MessageItem(
//                        message = message,
//                        modifier = Modifier.padding(vertical = 8.dp)
//                    )
//                }
//            }
//
//            MessageInput(
//                onSendMessage = { text ->
//                    viewModel.sendMessage(text)
//                },
//                modifier = Modifier.padding(16.dp)
//            )
//        }
//    }
//}
//
//@Composable
//fun MessageItem(
//    message: Message,
//    modifier: Modifier = Modifier
//) {
//    Column(
//        modifier = modifier
//            .fillMaxWidth()
//            .padding(horizontal = 8.dp),
//        horizontalAlignment = if (message.isCurrentUser) Alignment.End else Alignment.Start
//    ) {
//        if (!message.isCurrentUser) {
//            Text(
//                text = message.senderName,
//                style = MaterialTheme.typography.labelMedium,
//                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
//            )
//        }
//
//        Box(
//            modifier = Modifier
//                .clip(RoundedCornerShape(12.dp))
//                .background(
//                    if (message.isCurrentUser) MaterialTheme.colorScheme.primary
//                    else MaterialTheme.colorScheme.surfaceVariant
//                )
//                .padding(12.dp)
//        ) {
//            Column {
//                Text(
//                    text = message.value,
//                    color = if (message.isCurrentUser) MaterialTheme.colorScheme.onPrimary
//                    else MaterialTheme.colorScheme.onSurface
//                )
//                Text(
//                    text = message.createdAt.formatTime(),
//                    style = MaterialTheme.typography.labelSmall,
//                    color = if (message.isCurrentUser) MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f)
//                    else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
//                    modifier = Modifier.align(Alignment.End)
//                )
//            }
//        }
//    }
//}

@Composable
fun MessageInput(
    onSendMessage: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var text by remember { mutableStateOf("") }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp),
            placeholder = { Text("Введите сообщение") },
            singleLine = false,
            maxLines = 3
        )

        IconButton(
            onClick = {
                if (text.isNotBlank()) {
                    onSendMessage(text)
                    text = ""
                }
            },
            enabled = text.isNotBlank()
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.Send,
                contentDescription = "Отправить",
                tint = if (text.isNotBlank()) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
            )
        }
    }
}

@Composable
fun ChatItem(
    chat: Chat,
    lastMessage: Message?,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .background(Color(0xFF1E3D58)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = chat.name.take(1).uppercase(),
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = chat.name,
                fontFamily = fontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            lastMessage?.let { message ->
                Text(
                    text = message.value,
                    fontFamily = fontFamily,
                    fontSize = 14.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = if (message.viewedAt == null) Color.Black else Color.Gray
                )
            } ?: Text(
                text = "Нет сообщений",
                fontFamily = fontFamily,
                fontSize = 14.sp,
                color = Color.Gray
            )
        }

        lastMessage?.let { message ->
            Text(
                text = message.createdAt.formatTime(),
                fontFamily = fontFamily,
                fontSize = 12.sp,
                color = Color.Gray
            )
        }
    }
}

fun String.formatTime(): String {
    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        val date = inputFormat.parse(this) ?: return this
        val outputFormat = SimpleDateFormat("dd.MM.yy HH:mm", Locale.getDefault())
        outputFormat.format(date)
    } catch (e: Exception) {
        this
    }
}

@Composable
fun NewChatScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Chat screen")
    }
}

@Composable
fun ProfileScreen() {
    var name by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Box(modifier = Modifier.background(Color(0xFFE8EEF1))) {
        Row {
            IconButton(
                onClick = { },
                modifier = Modifier
                    .size(200.dp)
                    .padding(40.dp)
                    .background(
                        color = Color.LightGray,
                        shape = CircleShape
                    )
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Добавить",
                    tint = Color.White
                )
            }
            Column(modifier = Modifier
                .absolutePadding(0.dp, 64.dp, 20.dp))
            {
                Text(modifier = Modifier.fillMaxWidth(),
                    text = "Анастасия Игоревна",
                    fontFamily = fontFamily,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Text(modifier = Modifier.fillMaxWidth(),
                    text = "starryskies23",
                    fontFamily = fontFamily,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Light,
                    textAlign = TextAlign.Center
                )
            }
        }

        Column(modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(modifier = Modifier.fillMaxWidth(),
                text = "Данные аккаунта",
                fontFamily = fontFamily,
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(16.dp))

            Box(modifier = Modifier
                .background(Color.White)
                .border(0.dp, color = Color.Gray)
                .padding(20.dp)) {
                Column {
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = {
                            Text(
                                "Имя",
                                fontFamily = fontFamily,
                                fontWeight = FontWeight.Normal
                            )
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White
                        ),
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = {
                            Text(
                                "Пароль",
                                fontFamily = fontFamily,
                                fontWeight = FontWeight.Normal
                            )
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White
                        ),
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        visualTransformation = PasswordVisualTransformation()
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = { },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = name.isNotBlank() || password.isNotBlank(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF1E3D58),
                            disabledContainerColor = Color(0xFFBDBDBD)
                        )
                    ) {
                    Text("Подтвердить",
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                fontFamily = fontFamily,
                fontSize = 10.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.SemiBold,
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.Light
                        )
                    ) {
                        append("Нажимая на кнопку Потвердить вы соглашаетесь с ")
                    }
                    append("Условиями пользования")
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.Light
                        )
                    ) {
                        append(" и ")
                    }
                    append("Политикой конфиденциальности")
                }
            )
        }
    }
}


@Preview
@Composable
fun HomeScreenPreview() {
    val sampleChats = listOf(
        Chat(
            id = 1,
            name = "Иван Иванов",
            owner = 1,
            deleted = false,
            createdAt = "2023-10-01T10:00:00",
        ) to Message(
            id = 1,
            chatMemberId = 1,
            value = "Добрый день",
            type = MessageType.TEXT,
            createdAt = "2025-04-06T12:30:00",
            viewedAt = null,
            isDeleted = false,
            deletedAt = null
        ),
        Chat(
            id = 2,
            name = "Команда разработки",
            owner = 2,
            deleted = false,
            createdAt = "2023-09-15T09:00:00",
        ) to Message(
            id = 2,
            chatMemberId = 2,
            value = "Собрание в 15:00",
            type = MessageType.TEXT,
            createdAt = "2024-12-21T10:45:00",
            viewedAt = "2023-12-21T10:50:00",
            isDeleted = false,
            deletedAt = null
        )
    )

    MaterialTheme {
        HomeScreen(chats = sampleChats)
    }
}