package com.example.chatapp.presentation.ui

sealed class Screen(val route: String) {
    data object ServerIpScreen : Screen("server_ip")
    data object LoginScreen : Screen("login")
    data object RegistrationScreen : Screen("registration")
    data object CompleteRegistrationScreen : Screen("complete_registration")
}