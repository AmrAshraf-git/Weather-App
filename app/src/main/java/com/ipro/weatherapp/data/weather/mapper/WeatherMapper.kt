package com.ipro.weatherapp.data.weather.mapper

import data.weather.model.WeatherDto
import com.ipro.weatherapp.domain.exception.NoWeatherFoundException
import com.ipro.weatherapp.domain.model.CurrentWeatherData
import com.ipro.weatherapp.domain.model.DailyForecast
import com.ipro.weatherapp.domain.model.HourlyTemperature
import com.ipro.weatherapp.domain.model.Weather
import com.ipro.weatherapp.domain.model.WeatherCondition
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

class WeatherMapper {
    fun mapDtoToWeather(weatherDto: WeatherDto): Weather {
        val currentWeather = weatherDto.currentWeather ?: throw NoWeatherFoundException()
        val weatherCode = currentWeather.weatherCode ?: throw NoWeatherFoundException()

        val hourlyWeather = weatherDto.hourlyWeather ?: throw NoWeatherFoundException()
        val hourlyTemperature = hourlyWeather.temperature2m ?: throw NoWeatherFoundException()
        val hourlyTime = hourlyWeather.time ?: throw NoWeatherFoundException()

        val dailyWeather = weatherDto.dailyWeather ?: throw NoWeatherFoundException()
        val time = dailyWeather.time ?: throw NoWeatherFoundException()
        val maxTemps = dailyWeather.temperature2mMax ?: throw NoWeatherFoundException()
        val minTemps = dailyWeather.temperature2mMin ?: throw NoWeatherFoundException()
        val codes = dailyWeather.weatherCode ?: throw NoWeatherFoundException()

        if (time.size != maxTemps.size || time.size != minTemps.size || time.size != codes.size) {
            throw NoWeatherFoundException()
        }

        val currentData = CurrentWeatherData(
            temperature = currentWeather.temperature2m?.roundToInt() ?: 0,
            apparentTemperature = currentWeather.apparentTemperature?.roundToInt() ?: 0,
            windSpeed = currentWeather.windSpeed10m?.roundToInt() ?: 0,
            humidity = currentWeather.relativeHumidity2m ?: 0,
            uvIndex = currentWeather.uvIndex?.roundToInt() ?: 0,
            pressure = currentWeather.surfacePressure?.roundToInt() ?: 0,
            weatherCondition = getWeatherForeCast(weatherCode)
        )

        val hourlyData = hourlyTemperature.zip(hourlyTime) { temp, times ->
            HourlyTemperature(temp, getHourFromTimeString(times))
        }

        val dailyForecasts = time.indices.map { i ->
            DailyForecast(
                date = LocalDate.parse(time[i]),
                maxTemp = maxTemps[i].roundToInt(),
                minTemp = minTemps[i].roundToInt(),
                weatherCondition = getWeatherForeCast(codes[i])
            )
        }.take(7)

        return Weather(
            current = currentData,
            hourlyTemperatures = hourlyData,
            dailyForecasts = dailyForecasts
        )
    }

    private fun getHourFromTimeString(time: String): Int {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")
        val dateTime = LocalDateTime.parse(time, formatter)
        return dateTime.hour
    }

    private fun getWeatherForeCast(weatherCode: Int): WeatherCondition {
        return when (weatherCode) {
            0 -> WeatherCondition.CLEAR_SKY
            1 -> WeatherCondition.MAINLY_CLEAR
            2 -> WeatherCondition.PARTLY_CLOUDY
            3 -> WeatherCondition.OVERCAST
            45 -> WeatherCondition.FOG
            48 -> WeatherCondition.DEPOSITING_RIME_FOG
            51 -> WeatherCondition.DRIZZLE_LIGHT
            53 -> WeatherCondition.DRIZZLE_MODERATE
            55 -> WeatherCondition.DRIZZLE_HIGH
            56 -> WeatherCondition.FREEZING_DRIZZLE_LIGHT
            57 -> WeatherCondition.FREEZING_DRIZZLE_HIGHT
            61 -> WeatherCondition.RAIN_LIGHT
            63 -> WeatherCondition.RAIN_MODERATE
            65 -> WeatherCondition.RAIN_HEAVY
            66 -> WeatherCondition.FREEZING_RAIN_LIGHT
            67 -> WeatherCondition.FREEZING_RAIN_HIGH
            73 -> WeatherCondition.SNOW_MODERATE
            71 -> WeatherCondition.SNOW_LIGHT
            75 -> WeatherCondition.SNOW_HEAVY
            77 -> WeatherCondition.SNOW_GRAINS
            80 -> WeatherCondition.RAIN_SHOWER_LIGHT
            81 -> WeatherCondition.RAIN_SHOWER_MODRATE
            82 -> WeatherCondition.RAIN_SHOWER_HEAVY
            85 -> WeatherCondition.SNOW_SHOWER_LIGHT
            86 -> WeatherCondition.SNOW_SHOWER_HEAVY
            95 -> WeatherCondition.THUNDER_STORM
            96 -> WeatherCondition.THUNDER_STORM_HAIL_LIGHT
            99 -> WeatherCondition.THUNDER_STORM_HAIL_HEAVY
            else -> WeatherCondition.UNKNOWN_WEATHER_FORECAST
        }
    }
}