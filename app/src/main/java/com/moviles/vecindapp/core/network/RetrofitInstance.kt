package com.moviles.vecindapp.core.network

import com.moviles.vecindapp.core.token.TokenManager
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "https://vecindappback-production.up.railway.app"

    //interceptor para colocar el token en las peticiones
    private val authInterceptor = Interceptor { chain ->
        val original = chain.request()
        val builder = original.newBuilder()
        if (TokenManager.token.isNotEmpty()) {
            builder.header("Authorization", "Bearer ${TokenManager.token}")
        }
        val request = builder.build()
        chain.proceed(request)
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
        .build()

    val api: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}