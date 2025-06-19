package com.example.chatapp.presentation.ui

import android.os.Bundle
import android.widget.Toast
import com.example.chatapp.R
import androidx.compose.runtime.*
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.dp
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.Font
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.background
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.appcompat.app.AppCompatActivity
import com.example.chatapp.ui.theme.ChatAppTheme
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.PasswordVisualTransformation

val fontFamily = FontFamily(
    Font(R.font.inter_regular, FontWeight.Normal),
    Font(R.font.inter_light, FontWeight.Light),
    Font(R.font.inter_bold, FontWeight.Bold),
    Font(R.font.inter_semibold, FontWeight.SemiBold),
    Font(R.font.inter_thin, FontWeight.Thin)
)

class RegistrationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChatAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    RegistrationScreen { name, login, password ->
//                        viewModel.register(name, login, password)
                    }
                }
            }
        }
    }
}

@Composable
fun RegistrationScreen(
    onRegisterClick: (name: String, login: String, password: String) -> Unit
) {
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(true) }
    var ipAdress by remember { mutableStateOf("") }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false),
            title = {
                Text(
                    "Подключение к серверу",
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp
                )
            },
            text = {
                Column {
                    Text(
                        "Введите IP-адрес сервера:",
                        fontFamily = fontFamily,
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    OutlinedTextField(
                        value = ipAdress,
                        onValueChange = { ipAdress = it },
                        label = {
                            Text(
                                "Адрес",
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
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (ipAdress.isNotBlank()) {
                            showDialog = false
                        } else {
                            Toast.makeText(context, "Введите адрес сервера", Toast.LENGTH_SHORT).show()
                        }
                    },
                    enabled = ipAdress.isNotBlank(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFBDBDBD)
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        "Продолжить",
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        )
    }

    var name by remember { mutableStateOf("") }
    var login by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .background(Color(0xFFE8EEF1))
            .fillMaxSize()
            .padding(horizontal = 32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            "ChatApp",
            fontSize = 40.sp,
            fontFamily = fontFamily,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(100.dp))

        Text(
            fontFamily = fontFamily,
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Light,
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp
                    )
                ) {
                    append("Создать аккаунт\n")
                }
                append("Введите отображаемое имя, логин и пароль")
            }
        )

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text(
                "Имя",
                fontFamily = fontFamily,
                fontWeight = FontWeight.Normal
            ) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            ),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = login,
            onValueChange = { login = it },
            label = { Text("Логин",
                fontFamily = fontFamily,
                fontWeight = FontWeight.Normal
                ) },
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
            label = { Text("Пароль",
                fontFamily = fontFamily,
                fontWeight = FontWeight.Normal
                ) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            ),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (name.isNotBlank() && login.isNotBlank() && password.isNotBlank()) {
                    onRegisterClick(name, login, password)
                } else {
                    Toast.makeText(context, "Заполните все поля", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = name.isNotBlank() && login.isNotBlank() && password.isNotBlank(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF1E3D58),
                disabledContainerColor = Color(0xFFBDBDBD)
            )
        ) {
            Text("Продолжить",
                fontFamily = fontFamily,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            fontFamily = fontFamily,
            fontSize = 8.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.SemiBold,
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        fontWeight = FontWeight.Light
                    )
                ) {
                    append("Нажимая на кнопку Продолжить вы соглашаетесь с ")
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

@Preview(showBackground = true)
@Composable
fun PreviewRegistrationScreen() {
    MaterialTheme {
        RegistrationScreen(onRegisterClick = { _, _, _ -> })
    }
}