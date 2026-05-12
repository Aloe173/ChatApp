package com.example.chatapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatapp.domain.model.RegistrationData
import com.example.chatapp.domain.model.ServerConfig
import com.example.chatapp.domain.model.UserCredentials
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {
    private val _serverConfig = MutableStateFlow(ServerConfig())
    val serverConfig: StateFlow<ServerConfig> = _serverConfig.asStateFlow()

    private val _loginData = MutableStateFlow(UserCredentials())
    val loginData: StateFlow<UserCredentials> = _loginData.asStateFlow()

    private val _registrationData = MutableStateFlow(RegistrationData())
    val registrationData: StateFlow<RegistrationData> = _registrationData.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    private val _shouldNavigateToMain = MutableStateFlow(false)
    val shouldNavigateToMain: StateFlow<Boolean> = _shouldNavigateToMain.asStateFlow()

    private val _loginTrigger = MutableStateFlow(false)
    val loginTrigger: StateFlow<Boolean> = _loginTrigger.asStateFlow()

    private val _registrationTrigger = MutableStateFlow(false)
    val registrationTrigger: StateFlow<Boolean> = _registrationTrigger.asStateFlow()

    fun updateServerIp(ip: String) {
        _serverConfig.value = _serverConfig.value.copy(ipAddress = ip)
    }

    fun updateLogin(login: String) {
        _loginData.value = _loginData.value.copy(login = login)
    }

    fun updatePassword(password: String) {
        _loginData.value = _loginData.value.copy(password = password)
    }

    fun updateRegistrationData(registrationData: RegistrationData) {
        _registrationData.value = registrationData
    }


    fun updateRegistrationField(
        login: String? = null,
        password: String? = null,
        nickname: String? = null,
        firstName: String? = null,
        lastName: String? = null,
        phoneNumber: String? = null,
        email: String? = null
    ) {
        val current = _registrationData.value
        _registrationData.value = current.copy(
            login = login ?: current.login,
            password = password ?: current.password,
            nickname = nickname ?: current.nickname,
            firstName = firstName ?: current.firstName,
            lastName = lastName ?: current.lastName,
            phoneNumber = phoneNumber ?: current.phoneNumber
        )
    }

    fun validateIpAddress(): Boolean {
        val ipRegex = Regex("^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\$")  // Добавить вариант с доменным именем
        return ipRegex.matches(_serverConfig.value.ipAddress)
    }

    fun validateLoginCredentials(): Boolean {
        return _loginData.value.login.isNotBlank() &&
                _loginData.value.password.length >= 6
    }

    fun validateBasicRegistration(): Boolean {
        val data = _registrationData.value
        return data.login.isNotBlank() &&
                data.password.length >= 6 &&
                data.nickname.isNotBlank()
    }

    fun validateFullRegistration(): Boolean {
        val data = _registrationData.value
        return validateBasicRegistration() &&
                data.firstName.isNotBlank() &&
                data.lastName.isNotBlank() &&
                data.phoneNumber.matches(Regex("^\\+?[1-9][0-9]{7,14}\$"))
    }

    fun triggerLogin() {
        _loginTrigger.value = true
        _errorMessage.value = null
    }

    fun triggerRegistration() {
        _registrationTrigger.value = true
        _errorMessage.value = null
    }

    fun resetTriggers() {
        _loginTrigger.value = false
        _registrationTrigger.value = false
        _shouldNavigateToMain.value = false
    }

    suspend fun performLogin(): Boolean {
        return try {
            _isLoading.value = true
            // Имитация сетевого запроса
            kotlinx.coroutines.delay(1500)

            // Здесь будет реальная логика авторизации
            // Например:
            // val response = apiService.login(loginData.value)
            // if (response.isSuccess) { ... }

            _isLoading.value = false
            _shouldNavigateToMain.value = true
            true
        } catch (e: Exception) {
            _isLoading.value = false
            _errorMessage.value = "Ошибка при входе: ${e.message}"
            false
        }
    }

    suspend fun performRegistration(): Boolean {
        return try {
            _isLoading.value = true
            // Имитация сетевого запроса
            kotlinx.coroutines.delay(1500)

            // Здесь будет реальная логика регистрации

            _isLoading.value = false
            _shouldNavigateToMain.value = true
            true
        } catch (e: Exception) {
            _isLoading.value = false
            _errorMessage.value = "Ошибка при регистрации: ${e.message}"
            false
        }
    }

    fun clearError() {
        _errorMessage.value = null
    }
}