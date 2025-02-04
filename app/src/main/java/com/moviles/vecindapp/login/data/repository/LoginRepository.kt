package com.moviles.vecindapp.login.data.repository

import com.moviles.vecindapp.login.data.model.LoginResponse

interface LoginRepository {
    suspend fun login(email: String, password: String): LoginResponse
}