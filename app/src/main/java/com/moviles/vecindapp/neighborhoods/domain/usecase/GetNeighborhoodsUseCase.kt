package com.moviles.vecindapp.neighborhoods.domain.usecase

import com.moviles.vecindapp.neighborhoods.data.repository.NeighborhoodRepository

class GetNeighborhoodsUseCase(private val repository: NeighborhoodRepository) {
    suspend operator fun invoke() =
        repository.getNeighborhoods()
}