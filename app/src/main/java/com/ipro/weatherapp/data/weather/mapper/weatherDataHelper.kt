package com.ipro.weatherapp.data.weather.mapper

import com.ipro.weatherapp.domain.model.WeatherCondition
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun getHourFromTimeString(time: String): Int {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")
    val dateTime = LocalDateTime.parse(time, formatter)
    return dateTime.hour
}

fun getWeatherForeCast(weatherCode: Int): WeatherCondition {
    return when (weatherCode) {
        0 -> WeatherCondition.CLEAR_SKY
        1 -> WeatherCondition.MAINLY_CLEAR
        2 -> WeatherCondition.PARTLY_CLOUDY
        3 -> WeatherCondition.OVERCAST
        45 -> WeatherCondition.FOG
        48 -> WeatherCondition.DEPOSITING_RIME_FOG
        51 -> WeatherCondition.LIGHT_DRIZZLE
        53 -> WeatherCondition.MODERATE_DRIZZLE
        55 -> WeatherCondition.DENSE_DRIZZLE
        56 -> WeatherCondition.LIGHT_FREEZING_DRIZZLE
        57 -> WeatherCondition.DENSE_FREEZING_DRIZZLE
        61 -> WeatherCondition.SLIGHT_RAIN
        63 -> WeatherCondition.MODERATE_RAIN
        65 -> WeatherCondition.HEAVY_RAIN
        66 -> WeatherCondition.LIGHT_FREEZING_RAIN
        67 -> WeatherCondition.HEAVY_FREEZING_RAIN
        71 -> WeatherCondition.SLIGHT_SNOW_FALL
        73 -> WeatherCondition.MODERATE_SNOW_FALL
        75 -> WeatherCondition.HEAVY_SNOW_FALL
        77 -> WeatherCondition.SNOW_GRAINS
        80 -> WeatherCondition.SLIGHT_RAIN_SHOWERS
        81 -> WeatherCondition.MODERATE_RAIN_SHOWERS
        82 -> WeatherCondition.VIOLENT_RAIN_SHOWERS
        85 -> WeatherCondition.SLIGHT_SNOW_SHOWERS
        86 -> WeatherCondition.HEAVY_SNOW_SHOWERS
        95 -> WeatherCondition.SLIGHT_OR_MODERATE_THUNDERSTORM
        96 -> WeatherCondition.THUNDERSTORM_WITH_SLIGHT_HAIL
        99 -> WeatherCondition.THUNDERSTORM_WITH_HEAVY_HAIL
        else -> WeatherCondition.UNKNOWN_WEATHER_FORECAST
    }
}