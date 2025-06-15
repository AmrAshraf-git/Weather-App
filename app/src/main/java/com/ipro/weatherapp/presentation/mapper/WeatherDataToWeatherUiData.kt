package com.ipro.weatherapp.presentation.mapper

import com.ipro.weatherapp.domain.model.CurrentWeatherData
import com.ipro.weatherapp.domain.model.DailyWeatherData
import com.ipro.weatherapp.domain.model.HourlyTemperatureData
import com.ipro.weatherapp.presentation.model.CurrentWeatherUiData
import com.ipro.weatherapp.presentation.model.DailyWeatherUiData
import com.ipro.weatherapp.presentation.model.HourlyTemperatureUiData
import com.ipro.weatherapp.presentation.model.TimeTheme

fun CurrentWeatherData.toUiModel(): CurrentWeatherUiData {
    val theme = isDay.toTimeTheme()
    return CurrentWeatherUiData(
        temperature = this.temperature,
        feelsLike = this.feelsLike,
        windSpeed = this.windSpeed,
        humidity = this.humidity,
        uvIndex = this.uvIndex,
        pressure = this.pressure,
        isDay = this.isDay,
        rain = this.rain,
        weatherCondition = this.weatherCondition.getWeatherDescription(),
        weatherImage = this.weatherCondition.getWeatherImage(theme)
    )
}

fun DailyWeatherData.toUiModel(timeTheme: TimeTheme): DailyWeatherUiData {
    return DailyWeatherUiData(
        date = this.date,
        maxTemp = this.maxTemp,
        minTemp = this.minTemp,
        weatherCondition = this.weatherCondition.getWeatherDescription(),
        weatherImage = this.weatherCondition.getWeatherImage(timeTheme)
    )
}

fun HourlyTemperatureData.toUiModel(timeTheme: TimeTheme): HourlyTemperatureUiData {
    return HourlyTemperatureUiData(
        temperature = this.temperature,
        hour = this.hour,
        weatherCondition = this.weatherCondition.getWeatherDescription(),
        weatherImage = this.weatherCondition.getWeatherImage(timeTheme)
    )
}
