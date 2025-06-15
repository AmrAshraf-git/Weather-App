package com.ipro.weatherapp.domain.usecase

import com.ipro.weatherapp.domain.model.LocationCoordinate
import com.ipro.weatherapp.domain.repository.LocationRepo

class GetCityNameFromLocationUseCase(
    private val locationRepo: LocationRepo
) {
    suspend operator fun invoke(location: LocationCoordinate): String {
        return locationRepo.getCityNameFromLocation(location)
    }

}