package com.ipro.weatherapp.data.weather.remote

import com.ipro.weatherapp.domain.model.LocationCoordinate
import data.weather.model.WeatherDto

interface WeatherRemoteDataSource {
    suspend fun getDailyWeatherByCoordinate(locationCoordinate: LocationCoordinate): WeatherDto
}