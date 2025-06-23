package com.ipro.weatherapp.presentation.model

data class WeatherUiState(
    val isLoading: Boolean = true,
    val error: String? = null,
    val weather: WeatherUi? = null,
    val cityName: String = ""
)
