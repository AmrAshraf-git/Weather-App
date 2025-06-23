package com.ipro.weatherapp.data.weather.remote

import com.ipro.weatherapp.domain.model.LocationCoordinate
import data.weather.model.WeatherResponse

interface WeatherRemoteDataSource {
    suspend fun getDailyWeatherByCoordinate(locationCoordinate: LocationCoordinate): WeatherResponse
}