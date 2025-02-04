package com.moviles.vecindapp.register.data.repository

import com.moviles.vecindapp.core.network.RetrofitInstance
import com.moviles.vecindapp.register.data.datasource.AuthService
import com.moviles.vecindapp.register.data.model.RegisterRequest
import com.moviles.vecindapp.register.data.model.RegisterResponse

interface RegisterRepository {
    suspend fun register(email: String, password: String): RegisterResponse
}

class RegisterRepositoryImpl : RegisterRepository {
    private val authService: AuthService =
        RetrofitInstance.api.create(AuthService::class.java)

    override suspend fun register(email: String, password: String): RegisterResponse {
        val request = RegisterRequest(email, password)
        return authService.register(request)
    }
}
