package com.moviles.vecindapp.register.domain.usecase

import com.moviles.vecindapp.register.data.repository.RegisterRepository

class RegisterUseCase(private val repository: RegisterRepository) {
    suspend operator fun invoke(email: String, password: String) = repository.register(email, password)
}