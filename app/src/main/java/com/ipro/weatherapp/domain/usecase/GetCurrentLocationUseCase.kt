package com.ipro.weatherapp.domain.usecase

import com.ipro.weatherapp.domain.model.LocationCoordinate
import com.ipro.weatherapp.domain.repository.LocationRepo

class GetCurrentLocationUseCase(
    private val locationRepo: LocationRepo
) {
    suspend operator fun invoke(): Result<LocationCoordinate> {
        return runCatching { locationRepo.getCurrentLocation() }
    }

}