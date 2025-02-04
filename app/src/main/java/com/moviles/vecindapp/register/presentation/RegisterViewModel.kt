package com.moviles.vecindapp.register.presentation


import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moviles.vecindapp.register.data.repository.RegisterRepositoryImpl
import com.moviles.vecindapp.register.domain.usecase.RegisterUseCase
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {

    private val registerUseCase = RegisterUseCase(RegisterRepositoryImpl())

    var email = mutableStateOf("")
    var password = mutableStateOf("")
    var registrationSuccess = mutableStateOf(false)
    var errorMessage = mutableStateOf("")



    fun onRegister() {
        viewModelScope.launch {
            try {
                val response = registerUseCase(email.value, password.value)
                registrationSuccess.value = response.success
            } catch (e: Exception) {
                errorMessage.value = "Usuario existente."
            }
        }
    }

    fun setErrorMessage(message: String) {
        errorMessage.value = message
    }
}
