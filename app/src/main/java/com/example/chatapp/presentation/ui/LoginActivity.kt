package com.example.chatapp.presentation.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.util.Patterns
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.chatapp.presentation.viewmodel.AuthViewModel
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.chatapp.ui.theme.ChatAppTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment

fun isValidIpAddress(ip: String): Boolean {
    val pattern = Patterns.IP_ADDRESS
    return pattern.matcher(ip).matches()
}

fun isValidPhoneNumber(phone: String): Boolean {
    val pattern = Patterns.PHONE
    return pattern.matcher(phone).matches()
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChatAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AuthApp()
                }
            }
        }
    }
}

@Composable
fun AuthApp() {
    val navController = rememberNavController()
    val viewModel: AuthViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = Screen.ServerIpScreen.route
    ) {
        composable(Screen.ServerIpScreen.route) {
            ServerIpScreen(navController, viewModel)
        }
        composable(Screen.LoginScreen.route) {
            LoginScreen(navController, viewModel)
        }
        composable(Screen.RegistrationScreen.route) {
            RegistrationScreen(navController, viewModel)
        }
        composable(Screen.CompleteRegistrationScreen.route) {
            CompleteRegistrationScreen(navController, viewModel)
        }
    }
}

@Composable
fun ServerIpScreen(
    navController: NavHostController,
    viewModel: AuthViewModel
) {
    val serverConfig by viewModel.serverConfig.collectAsState()
    var ipAddress by remember { mutableStateOf(serverConfig.ipAddress) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Плейсхолдер под лого
        Text(
            text = "Connect",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 32.dp)

        )

        Text(
            text = "Укажите адрес сервера",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 32.dp)

        )

        OutlinedTextField(
            value = ipAddress,
            onValueChange = { ipAddress = it },
            label = { Text("Адрес: IP или доменное имя") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("192.168.0.1 | corp.chat") }
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Сканер QR-кода
        Button(
            onClick = { },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Сканировать QR-код")
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                viewModel.updateServerIp(ipAddress)
                if (viewModel.validateIpAddress()) {    // Переработать валидацию
                    navController.navigate(Screen.LoginScreen.route)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = ipAddress.isNotBlank()
        ) {
            Text("Продолжить")
        }
    }
}

@SuppressLint("ContextCastToActivity")
@Composable
fun LoginScreen(
    navController: NavHostController,
    viewModel: AuthViewModel
) {
    val loginData by viewModel.loginData.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val loginTrigger by viewModel.loginTrigger.collectAsState()
    val shouldNavigateToMain by viewModel.shouldNavigateToMain.collectAsState()
    val context = LocalContext.current

    // Обработка навигации после успешного входа
    LaunchedEffect(shouldNavigateToMain) {
        if (shouldNavigateToMain) {
            val intent = Intent(context, ChatActivity::class.java)
            context.startActivity(intent)
            (context as? Activity)?.finish()
        }
    }


    // Обработка логина при срабатывании триггера
    LaunchedEffect(loginTrigger) {
        if (loginTrigger && viewModel.validateLoginCredentials()) {
            viewModel.performLogin()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Вход",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        errorMessage?.let { message ->
            Text(
                text = message,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        OutlinedTextField(
            value = loginData.login,
            onValueChange = { viewModel.updateLogin(it) },
            label = { Text("Логин") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = loginData.password,
            onValueChange = { viewModel.updatePassword(it) },
            label = { Text("Пароль") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                if (viewModel.validateLoginCredentials()) {
                    viewModel.triggerLogin()
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = viewModel.validateLoginCredentials() && !isLoading
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    strokeWidth = 2.dp,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            } else {
                Text("Войти")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(
            onClick = {
                navController.navigate(Screen.RegistrationScreen.route)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Регистрация")
        }
    }
}

@Composable
fun RegistrationScreen(
    navController: NavHostController,
    viewModel: AuthViewModel
) {
    val registrationData by viewModel.registrationData.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Connect",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        errorMessage?.let { message ->
            Text(
                text = message,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        OutlinedTextField(
            value = registrationData.login,
            onValueChange = { viewModel.updateRegistrationField(login = it) },
            label = { Text("Логин") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = registrationData.password,
            onValueChange = { viewModel.updateRegistrationField(password = it) },
            label = { Text("Пароль") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = registrationData.nickname,
            onValueChange = { viewModel.updateRegistrationField(nickname = it) },
            label = { Text("Никнейм") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                if (viewModel.validateBasicRegistration()) {
                    navController.navigate(Screen.CompleteRegistrationScreen.route)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = viewModel.validateBasicRegistration() && !isLoading
        ) {
            Text("Далее")
        }
    }
}

@Composable
fun CompleteRegistrationScreen(
    navController: NavHostController,
    viewModel: AuthViewModel
) {
    val registrationData by viewModel.registrationData.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val registrationTrigger by viewModel.registrationTrigger.collectAsState()
    val shouldNavigateToMain by viewModel.shouldNavigateToMain.collectAsState()
    val context = LocalContext.current

    // Обработка навигации после успешной регистрации
    LaunchedEffect(shouldNavigateToMain) {
        if (shouldNavigateToMain) {
            val intent = Intent(context, ChatActivity::class.java)
            context.startActivity(intent)
            (context as? Activity)?.finish()
        }
    }

    // Обработка регистрации при срабатывании триггера
    LaunchedEffect(registrationTrigger) {
        if (registrationTrigger && viewModel.validateFullRegistration()) {
            viewModel.performRegistration()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Connect",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        errorMessage?.let { message ->
            Text(
                text = message,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = registrationData.lastName,
                onValueChange = { viewModel.updateRegistrationField(lastName = it) },
                label = { Text("Фамилия") },
                singleLine = true,
                modifier = Modifier.weight(1f)
            )

            OutlinedTextField(
                value = registrationData.firstName,
                onValueChange = { viewModel.updateRegistrationField(firstName = it) },
                label = { Text("Имя") },
                singleLine = true,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = registrationData.phoneNumber,
            onValueChange = { viewModel.updateRegistrationField(phoneNumber = it) },
            label = { Text("Номер телефона") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("+7 (XXX) XXX-XX-XX") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = registrationData.email,
            onValueChange = { viewModel.updateRegistrationField(email = it) },
            label = { Text("Электронная почта") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("mail@mail.mail") }
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                if (viewModel.validateFullRegistration()) {
                    viewModel.triggerRegistration()
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = viewModel.validateFullRegistration() && !isLoading
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    strokeWidth = 2.dp,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            } else {
                Text("Завершить регистрацию")
            }
        }
    }
}

@SuppressLint("ViewModelConstructorInComposable", "SuspiciousIndentation")
@Preview(showBackground = true, showSystemUi = true, name = "Step-by-Step Tutorial")
@Composable
fun PreviewAll() {
    var currentStep by remember { mutableIntStateOf(0) }
    val steps = listOf(
        "1. Ввод IP-адреса",
        "2. Вход в систему",
        "3. Регистрация",
        "4. Ввод ПД"
    )

    val navController = rememberNavController()
    val viewModel = AuthViewModel()

    // Панель управления
//    ChatAppTheme {
//        Column(modifier = Modifier.fillMaxSize()) {
//            Card(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(8.dp),
//                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
//            ) {
//                Column(modifier = Modifier.padding(8.dp)) {
//                    Text(
//                        "Выбор экрана",
//                        style = MaterialTheme.typography.titleMedium
//                    )
//
//                    Text(
//                        steps[currentStep],
//                        style = MaterialTheme.typography.bodyMedium,
//                        modifier = Modifier.padding(vertical = 4.dp)
//                    )
//
//                    Row(
//                        modifier = Modifier.fillMaxWidth(),
//                        horizontalArrangement = Arrangement.SpaceBetween
//                    ) {
//                        Button(
//                            onClick = {
//                                if (currentStep > 0) {
//                                    currentStep--
//                                    navigateToStep(currentStep, navController)
//                                }
//                            },
//                            enabled = currentStep > 0
//                        ) {
//                            Text("Назад")
//                        }
//
//                        Button(
//                            onClick = {
//                                if (currentStep < steps.size - 1) {
//                                    currentStep++
//                                    navigateToStep(currentStep, navController)
//                                }
//                            },
//                            enabled = currentStep < steps.size - 1
//                        ) {
//                            Text("Вперед")
//                        }
//                    }
//                }
//            }

            // Основной контент
            NavHost(
                navController = navController,
                startDestination = Screen.ServerIpScreen.route
            ) {
                composable(Screen.ServerIpScreen.route) {
                    ServerIpScreen(navController = navController, viewModel = viewModel)
                }
                composable(Screen.LoginScreen.route) {
                    LoginScreen(navController = navController, viewModel = viewModel)
                }
                composable(Screen.RegistrationScreen.route) {
                    RegistrationScreen(navController = navController, viewModel = viewModel)
                }
                composable(Screen.CompleteRegistrationScreen.route) {
                    CompleteRegistrationScreen(navController = navController, viewModel = viewModel)
                }
            }
        }
    //}
//}

private fun navigateToStep(step: Int, navController: NavHostController) {
    when (step) {
        0 -> navController.navigate(Screen.ServerIpScreen.route) {
            popUpTo(Screen.ServerIpScreen.route) { inclusive = true }
        }
        1 -> navController.navigate(Screen.LoginScreen.route) {
            popUpTo(Screen.ServerIpScreen.route) { inclusive = false }
        }
        2 -> navController.navigate(Screen.RegistrationScreen.route) {
            popUpTo(Screen.ServerIpScreen.route) { inclusive = false }
        }
        3 -> navController.navigate(Screen.CompleteRegistrationScreen.route) {
            popUpTo(Screen.ServerIpScreen.route) { inclusive = false }
        }
    }
}