package com.ipro.weatherapp.domain.usecase

import com.ipro.weatherapp.domain.model.LocationCoordinate
import com.ipro.weatherapp.domain.model.Weather
import com.ipro.weatherapp.domain.repository.WeatherRepo

class GetWeatherByLocationUseCase(
    private val weatherRepo: WeatherRepo
) {
    suspend operator fun invoke(location: LocationCoordinate): Result<Weather> {
        return runCatching { weatherRepo.getDailyWeatherByCoordinate(location) }
    }
}