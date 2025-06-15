package com.ipro.weatherapp.data.weather.remote

import com.ipro.weatherapp.domain.model.LocationCoordinate
import data.weather.model.WeatherDto
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.json.Json

class WeatherRemoteDataSourceImp (private val client: HttpClient, private val json: Json,)
    : WeatherRemoteDataSource {
    override suspend fun getDailyWeatherByCoordinate(locationCoordinate: LocationCoordinate)
    : WeatherDto {
        val response = client.get(BASE_WEATHER_URL){
            url {
                parameters.append("latitude", locationCoordinate.latitude.toString())
                parameters.append("longitude", locationCoordinate.longitude.toString())
                parameters.append("current", CURRENT_TAGS)
                parameters.append("hourly", HOURLY_TAGS)
                parameters.append("daily", DAILY_TAGS)
                parameters.append("forecast_days", "7")
                parameters.append("timezone", "auto")
            }
        }
        return json.decodeFromString<WeatherDto>(response.bodyAsText())
    }

    companion object{
        private const val BASE_WEATHER_URL = "https://api.open-meteo.com/v1/forecast"

        private const val HOURLY_TAGS = "temperature_2m,weather_code,time"
        private const val DAILY_TAGS = "temperature_2m_max,temperature_2m_min,weather_code,time"
        private const val CURRENT_TAGS =
            "temperature_2m,weather_code,wind_speed_10m,relative_humidity_2m,precipitation_probability,uv_index,surface_pressure,apparent_temperature,is_day"
    }
}