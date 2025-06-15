package com.ipro.weatherapp.presentation.components

sealed class WeatherEvent {
    object LoadWeather : WeatherEvent()
    data class Retry(val reason: String) : WeatherEvent()
}