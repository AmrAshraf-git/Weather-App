package com.ipro.weatherapp.domain.model

import java.time.LocalDate

data class Weather(
    val hourlyWeatherData: List<HourlyWeatherData>,
    val currentWeatherData: CurrentWeatherData,
    val dailyWeatherData: List<DailyWeatherData>
)

data class CurrentWeatherData(
    val temperature: Int,
    val feelsLike: Int,
    val windSpeed: Int,
    val humidity: Int,
    val uvIndex: Int,
    val pressure: Int,
    val isDay: Boolean,
    val rain: Int,
    val weatherCondition: WeatherCondition,
)

data class DailyWeatherData(
    val date: LocalDate,
    val maxTemp: Int,
    val minTemp: Int,
    val weatherCondition: WeatherCondition,
)

data class HourlyWeatherData(
    val temperature: Int,
    val hour: Int,
    val weatherCondition: WeatherCondition,
)