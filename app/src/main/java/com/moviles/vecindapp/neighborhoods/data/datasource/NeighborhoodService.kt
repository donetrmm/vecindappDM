package com.moviles.vecindapp.neighborhoods.data.datasource

import com.moviles.vecindapp.neighborhoods.data.model.NeighborhoodRequest
import com.moviles.vecindapp.neighborhoods.data.model.NeighborhoodResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface NeighborhoodService {
    @POST("neighborhoods")
    suspend fun addNeighborhood(
        @Body request: NeighborhoodRequest
    ): NeighborhoodResponse

    @GET("neighborhoods")
    suspend fun getNeighborhoods(
    ): List<NeighborhoodResponse>
}
