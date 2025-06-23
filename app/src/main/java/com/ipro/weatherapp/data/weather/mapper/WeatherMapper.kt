package com.ipro.weatherapp.data.weather.mapper

import data.weather.model.WeatherResponse
import com.ipro.weatherapp.domain.exception.NoWeatherFoundException
import com.ipro.weatherapp.domain.model.CurrentWeatherData
import com.ipro.weatherapp.domain.model.DailyWeatherData
import com.ipro.weatherapp.domain.model.HourlyWeatherData
import com.ipro.weatherapp.domain.model.Weather
import data.weather.model.CurrentWeatherDto
import data.weather.model.DailyWeatherDto
import data.weather.model.HourlyWeatherDto
import java.time.LocalDate
import kotlin.math.roundToInt

object WeatherMapper {
    fun mapDtoToWeather(weatherResponse: WeatherResponse): Weather {
        val currentWeather = weatherResponse.currentWeatherDto ?: throw NoWeatherFoundException()
        val hourlyWeather = weatherResponse.hourlyWeatherDto ?: throw NoWeatherFoundException()
        val dailyWeather = weatherResponse.dailyWeatherDto ?: throw NoWeatherFoundException()

        val currentData = toCurrentWeatherData(currentWeather)
        val hourlyData = toHourlyWeatherData(hourlyWeather)
        val dailyData = toDailyWeatherData(dailyWeather)

        return Weather(
            currentWeatherData = currentData,
            hourlyWeatherData = hourlyData,
            dailyWeatherData = dailyData
        )
    }


    private fun toCurrentWeatherData(currentWeatherDto: CurrentWeatherDto): CurrentWeatherData {
        return CurrentWeatherData(
            temperature = currentWeatherDto.temperature2m?.roundToInt() ?: 0,
            feelsLike = currentWeatherDto.apparentTemperature?.roundToInt() ?: 0,
            windSpeed = currentWeatherDto.windSpeed10m?.roundToInt() ?: 0,
            humidity = currentWeatherDto.relativeHumidity2m ?: 0,
            uvIndex = currentWeatherDto.uvIndex?.roundToInt() ?: 0,
            pressure = currentWeatherDto.surfacePressure?.roundToInt() ?: 0,
            weatherCondition = getWeatherForeCast(currentWeatherDto.weatherCode ?: 0),
            isDay = currentWeatherDto.isDay?.equals(1) ?: true,
            rain = currentWeatherDto.precipitationProbability?.roundToInt() ?: 0
            //weatherImage = getWeatherImage(weatherCode),
            //weatherDescription = getWeatherDescription(weatherCode)
        )
    }

    private fun toHourlyWeatherData(hourlyWeatherDto: HourlyWeatherDto) : List<HourlyWeatherData> {

        val hourlyTemperature = hourlyWeatherDto.temperature2m ?: throw NoWeatherFoundException()
        val hourlyTime = hourlyWeatherDto.time ?: throw NoWeatherFoundException()
        val hourlyWeatherCode = hourlyWeatherDto.weatherCode ?: throw NoWeatherFoundException()
        if (hourlyTemperature.size != hourlyTime.size || hourlyTemperature.size != hourlyWeatherCode.size) {
            throw NoWeatherFoundException()
        }
        val minSize = listOf(hourlyTemperature.size, hourlyTime.size, hourlyWeatherCode.size)
            .minOrNull() ?: 0

        val hourlyData = (0 until minSize).map { i ->
            HourlyWeatherData(
                temperature = hourlyWeatherDto.temperature2m[i].roundToInt(),
                hour = getHourFromTimeString(hourlyTime[i]),
                weatherCondition = getWeatherForeCast(hourlyWeatherCode[i])
            )
        }.drop(1).take(24)

        return hourlyData
    }

    private fun toDailyWeatherData(dailyWeatherDto: DailyWeatherDto): List<DailyWeatherData> {
        val time = dailyWeatherDto.time ?: throw NoWeatherFoundException()
        if (time.size != dailyWeatherDto.temperature2mMax?.size ||
            time.size != dailyWeatherDto.temperature2mMin?.size ||
            time.size != dailyWeatherDto.weatherCode?.size
        ) { throw NoWeatherFoundException() }

        val maxTemps = dailyWeatherDto.temperature2mMax
        val minTemps = dailyWeatherDto.temperature2mMin
        val codes = dailyWeatherDto.weatherCode
        val dailyWeatherData = time.indices.map { i ->
            DailyWeatherData(
                date = LocalDate.parse(time[i]),
                maxTemp = maxTemps[i].roundToInt(),
                minTemp = minTemps[i].roundToInt(),
                weatherCondition = getWeatherForeCast(codes[i])
            )
        }.take(7)

        return dailyWeatherData
    }

}