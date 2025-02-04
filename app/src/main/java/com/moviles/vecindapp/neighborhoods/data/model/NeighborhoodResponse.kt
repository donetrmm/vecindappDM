package com.moviles.vecindapp.neighborhoods.data.model

data class NeighborhoodResponse(
    val id: Int,
    val nombre: String,
    val direccion: String,
    val colonia: String,
    val estado: String,
    val numeroCasas: Int
)