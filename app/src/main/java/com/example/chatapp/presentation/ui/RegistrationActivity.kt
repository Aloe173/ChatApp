package com.example.chatapp.presentation.ui

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.chatapp.R

val fontFamily = FontFamily(
    Font(R.font.inter_regular, FontWeight.Normal),
    Font(R.font.inter_light, FontWeight.Light),
    Font(R.font.inter_bold, FontWeight.Bold),
    Font(R.font.inter_semibold, FontWeight.SemiBold),
    Font(R.font.inter_thin, FontWeight.Thin)
)

@Composable
fun RegistrationScreen(
    onRegisterClick: (name: String, login: String, password: String) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var login by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val context = LocalContext.current

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
                containerColor = Color(0xFFBDBDBD),
                contentColor = Color.White,
                disabledContainerColor = Color(0xFF1E3D58)
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