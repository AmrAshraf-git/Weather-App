package com.ipro.weatherapp.presentation.weather

import com.ipro.weatherapp.domain.model.Weather

data class WeatherState(
    val isLoading: Boolean = false,
    val weather: Weather? = null,
    val errorMessage: String? = null
)
