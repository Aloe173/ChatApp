package com.example.chatapp.presentation.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.chatapp.R

class AuthorizationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_authorization)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}

@Composable
fun AuthorizationScreen(
    onAuthClick: (login: String, password: String) -> Unit
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

        Text("Вход",
            fontFamily = fontFamily,
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.SemiBold,
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
                if (login.isNotBlank() && password.isNotBlank()) {
                    onAuthClick(login, password)
                } else {
                    Toast.makeText(context, "Заполните все поля", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = login.isNotBlank() && password.isNotBlank(),
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
            fontSize = 10.sp,
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
fun PreviewAuthorizationScreen() {
    MaterialTheme {
        AuthorizationScreen(onAuthClick = { _, _ -> })
    }
}