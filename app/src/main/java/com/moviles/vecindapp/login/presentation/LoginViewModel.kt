package com.moviles.vecindapp.login.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moviles.vecindapp.core.token.TokenManager
import com.moviles.vecindapp.login.data.repository.LoginRepositoryImpl
import com.moviles.vecindapp.login.domain.usecase.LoginUseCase
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val loginUseCase = LoginUseCase(LoginRepositoryImpl())

    var email = mutableStateOf("")
    var password = mutableStateOf("")
    var token = mutableStateOf("")
    var errorMessage = mutableStateOf("")
    var loginError = mutableStateOf(false)

    fun onLogin() {
        viewModelScope.launch {
            try {
                val response = loginUseCase(email.value, password.value)
                token.value = response.access_token
                TokenManager.token = response.access_token

                errorMessage.value = ""
                loginError.value = false
            } catch (e: Exception) {
                errorMessage.value = e.message ?: "Error desconocido"
                loginError.value = true
            }
        }
    }
}
