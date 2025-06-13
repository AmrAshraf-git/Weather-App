package com.ipro.weatherapp.domain.model

import java.time.LocalDate

data class Weather(
    val hourlyTemperatures: List<HourlyTemperature>,
    //val weatherCondition: WeatherCondition,
    val current: CurrentWeatherData,
    val dailyForecasts: List<DailyForecast>
)

data class CurrentWeatherData(
    val temperature: Int,
    val apparentTemperature: Int,
    val windSpeed: Int,
    val humidity: Int,
    val uvIndex: Int,
    val pressure: Int,
    val weatherCondition: WeatherCondition
)

data class DailyForecast(
    val date: LocalDate,
    val maxTemp: Int,
    val minTemp: Int,
    val weatherCondition: WeatherCondition
)