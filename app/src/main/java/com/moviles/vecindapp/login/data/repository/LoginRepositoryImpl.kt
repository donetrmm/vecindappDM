package com.moviles.vecindapp.login.data.repository

import com.moviles.vecindapp.core.network.RetrofitInstance
import com.moviles.vecindapp.login.data.datasource.AuthService
import com.moviles.vecindapp.login.data.model.LoginRequest
import com.moviles.vecindapp.login.data.model.LoginResponse

class LoginRepositoryImpl : LoginRepository {
    private val authService: AuthService =
        RetrofitInstance.api.create(AuthService::class.java)

    override suspend fun login(email: String, password: String): LoginResponse {
        val request = LoginRequest(email, password)
        return authService.login(request)
    }
}