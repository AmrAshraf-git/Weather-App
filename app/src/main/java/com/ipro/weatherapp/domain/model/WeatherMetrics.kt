package com.ipro.weatherapp.domain.model

data class WeatherMetrics(
    val windSpeedKmh: Int,
    val humidityPercent: Int,
    val rainChancePercent: Int,
    val uvIndex: Int,
    val pressureHPa: Int
)
