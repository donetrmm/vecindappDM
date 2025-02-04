package com.moviles.vecindapp.neighborhoods.domain.usecase

import com.moviles.vecindapp.neighborhoods.data.model.NeighborhoodRequest
import com.moviles.vecindapp.neighborhoods.data.repository.NeighborhoodRepository

class AddNeighborhoodUseCase(private val repository: NeighborhoodRepository) {
    suspend operator fun invoke(request: NeighborhoodRequest) =
        repository.addNeighborhood(request)
}