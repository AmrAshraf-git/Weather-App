package com.ipro.weatherapp.di

import com.ipro.weatherapp.domain.usecase.GetCityNameFromLocationUseCase
import com.ipro.weatherapp.domain.usecase.GetCurrentLocationUseCase
import com.ipro.weatherapp.domain.usecase.GetWeatherByLocationUseCase
import org.koin.dsl.module

val domainModule = module {

    single {
        GetCurrentLocationUseCase(
            locationRepo = get()
        )
    }

    single {
        GetWeatherByLocationUseCase(
            weatherRepo = get(),
        )
    }
    single {
        GetCityNameFromLocationUseCase(
            locationRepo = get()
        )
    }
}