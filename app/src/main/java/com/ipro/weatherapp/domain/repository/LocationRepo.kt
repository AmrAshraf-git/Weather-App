package com.ipro.weatherapp.domain.repository

import com.ipro.weatherapp.domain.model.LocationCoordinate

interface LocationRepo {
    suspend fun getCurrentLocation(): LocationCoordinate
}