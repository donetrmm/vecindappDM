package com.moviles.vecindapp.neighborhoods.data.repository

import com.moviles.vecindapp.core.network.RetrofitInstance
import com.moviles.vecindapp.neighborhoods.data.datasource.NeighborhoodService
import com.moviles.vecindapp.neighborhoods.data.model.NeighborhoodRequest
import com.moviles.vecindapp.neighborhoods.data.model.NeighborhoodResponse

interface NeighborhoodRepository {
    suspend fun addNeighborhood(request: NeighborhoodRequest): NeighborhoodResponse
    suspend fun getNeighborhoods(): List<NeighborhoodResponse>
}

class NeighborhoodRepositoryImpl : NeighborhoodRepository {
    private val neighborhoodService: NeighborhoodService =
        RetrofitInstance.api.create(NeighborhoodService::class.java)

    override suspend fun addNeighborhood(request: NeighborhoodRequest): NeighborhoodResponse {
        return neighborhoodService.addNeighborhood(request)
    }

    override suspend fun getNeighborhoods(): List<NeighborhoodResponse> {
        return neighborhoodService.getNeighborhoods()
    }
}