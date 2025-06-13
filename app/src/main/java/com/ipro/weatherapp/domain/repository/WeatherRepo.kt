package com.ipro.weatherapp.domain.repository

import com.ipro.weatherapp.domain.model.LocationCoordinate
import com.ipro.weatherapp.domain.model.Weather

interface WeatherRepo {
    suspend fun getDailyWeatherByCoordinate(locationCoordinate: LocationCoordinate): Weather
}