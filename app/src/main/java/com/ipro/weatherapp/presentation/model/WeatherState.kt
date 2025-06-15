package com.ipro.weatherapp.presentation.model



data class WeatherState(
    val isLoading: Boolean = true,
    val error: String? = null,
    val currentWeather: CurrentWeatherUiData? = null,
    val hourlyTemperatureData: List<HourlyTemperatureUiData> = emptyList(),
    val dailyWeatherData: List<DailyWeatherUiData> = emptyList(),
    val cityName: String = ""
)
