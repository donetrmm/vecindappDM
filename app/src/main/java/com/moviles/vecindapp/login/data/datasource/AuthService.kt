package com.moviles.vecindapp.login.data.datasource

import com.moviles.vecindapp.login.data.model.LoginRequest
import com.moviles.vecindapp.login.data.model.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse
}