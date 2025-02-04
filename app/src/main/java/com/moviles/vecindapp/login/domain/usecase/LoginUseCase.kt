package com.moviles.vecindapp.login.domain.usecase

import com.moviles.vecindapp.login.data.repository.LoginRepository

class LoginUseCase(private val repository: LoginRepository) {
    suspend operator fun invoke(email: String, password: String) = repository.login(email, password)
}