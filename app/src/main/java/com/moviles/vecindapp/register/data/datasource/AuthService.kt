package com.moviles.vecindapp.register.data.datasource

import com.moviles.vecindapp.register.data.model.RegisterRequest
import com.moviles.vecindapp.register.data.model.RegisterResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("auth/register")
    suspend fun register(@Body request: RegisterRequest): RegisterResponse
}
