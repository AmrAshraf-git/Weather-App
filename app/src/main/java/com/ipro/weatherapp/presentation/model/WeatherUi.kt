package com.ipro.weatherapp.presentation.model

import com.ipro.weatherapp.presentation.util.UiImage
import com.ipro.weatherapp.presentation.util.UiText
import java.time.LocalDate

data class WeatherUi(
    val hourlyTemperatures: List<HourlyTemperatureUiData>,
    val current: CurrentWeatherUiData,
    val dailyForecasts: List<DailyWeatherUiData>
)

data class CurrentWeatherUiData(
    val temperature: Int,
    val feelsLike: Int,
    val windSpeed: Int,
    val humidity: Int,
    val uvIndex: Int,
    val pressure: Int,
    val isDay: Boolean,
    val rain: Int,
    val weatherCondition: UiText,
    val weatherImage: UiImage
)

data class DailyWeatherUiData(
    val date: LocalDate,
    val maxTemp: Int,
    val minTemp: Int,
    val weatherCondition: UiText,
    val weatherImage: UiImage
)

data class HourlyTemperatureUiData(
    val temperature: Int,
    val hour: Int,
    val weatherCondition: UiText,
    val weatherImage: UiImage
)