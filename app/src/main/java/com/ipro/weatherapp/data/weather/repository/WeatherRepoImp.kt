package com.ipro.weatherapp.data.weather.repository

import com.ipro.weatherapp.data.weather.mapper.WeatherMapper
import com.ipro.weatherapp.data.weather.remote.WeatherRemoteDataSource
import com.ipro.weatherapp.domain.model.LocationCoordinate
import com.ipro.weatherapp.domain.model.Weather
import com.ipro.weatherapp.domain.repository.WeatherRepo

class WeatherRepoImp(
    private val remoteDataSource: WeatherRemoteDataSource,
    private val weatherMapper: WeatherMapper,
): WeatherRepo {
    override suspend fun getDailyWeatherByCoordinate(locationCoordinate: LocationCoordinate): Weather {
        val res = remoteDataSource.getDailyWeatherByCoordinate(locationCoordinate)
        return weatherMapper.mapDtoToWeather(res)
    }

}