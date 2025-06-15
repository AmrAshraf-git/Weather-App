package com.ipro.weatherapp.data.weather.mapper

import data.weather.model.WeatherDto
import com.ipro.weatherapp.domain.exception.NoWeatherFoundException
import com.ipro.weatherapp.domain.model.CurrentWeatherData
import com.ipro.weatherapp.domain.model.DailyWeatherData
import com.ipro.weatherapp.domain.model.HourlyTemperatureData
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
        val hourlyWeatherCode = hourlyWeather.weatherCode ?: throw NoWeatherFoundException()

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
            feelsLike = currentWeather.apparentTemperature?.roundToInt() ?: 0,
            windSpeed = currentWeather.windSpeed10m?.roundToInt() ?: 0,
            humidity = currentWeather.relativeHumidity2m ?: 0,
            uvIndex = currentWeather.uvIndex?.roundToInt() ?: 0,
            pressure = currentWeather.surfacePressure?.roundToInt() ?: 0,
            weatherCondition = getWeatherForeCast(weatherCode),
            isDay = currentWeather.isDay?.equals(1)?:true,
            rain = currentWeather.precipitationProbability?.roundToInt() ?: 0
            //weatherImage = getWeatherImage(weatherCode),
            //weatherDescription = getWeatherDescription(weatherCode)

        )

        val minSize = listOf(hourlyTemperature.size, hourlyTime.size, hourlyWeatherCode.size).minOrNull() ?: 0
        val hourlyData = (0 until minSize).map { i ->
            HourlyTemperatureData(
                temperature = hourlyTemperature[i].roundToInt(),
                hour = getHourFromTimeString(hourlyTime[i]),
                weatherCondition = getWeatherForeCast(hourlyWeatherCode[i])
            )
        }.drop(1).take(24)

        val dailyWeatherData = time.indices.map { i ->
            DailyWeatherData(
                date = LocalDate.parse(time[i]),
                maxTemp = maxTemps[i].roundToInt(),
                minTemp = minTemps[i].roundToInt(),
                weatherCondition = getWeatherForeCast(codes[i])
            )
        }.take(7)

        return Weather(
            current = currentData,
            hourlyTemperatureData = hourlyData,
            dailyWeatherData = dailyWeatherData
        )
    }

    private fun getHourFromTimeString(time: String): Int {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")
        val dateTime = LocalDateTime.parse(time, formatter)
        return dateTime.hour
    }

    private fun getWeatherForeCast(weatherCode: Int): WeatherCondition {
        return when (weatherCode) {
            0 ->  WeatherCondition.CLEAR_SKY
            1 ->  WeatherCondition.MAINLY_CLEAR
            2 ->  WeatherCondition.PARTLY_CLOUDY
            3 ->  WeatherCondition.OVERCAST
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
}