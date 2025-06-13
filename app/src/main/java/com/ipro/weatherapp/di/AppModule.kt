package com.ipro.weatherapp.di

import com.google.android.gms.location.LocationServices
import com.ipro.weatherapp.data.location.repository.LocationRepoImp
import com.ipro.weatherapp.data.weather.mapper.WeatherMapper
import com.ipro.weatherapp.data.weather.repository.WeatherRepoImp
import com.ipro.weatherapp.domain.repository.LocationRepo
import com.ipro.weatherapp.domain.repository.WeatherRepo
import com.ipro.weatherapp.domain.usecase.GetCurrentLocationUseCase
import com.ipro.weatherapp.domain.usecase.GetWeatherByLocationUseCase
import com.ipro.weatherapp.presentation.weather.viewModel.WeatherViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single {
        LocationServices.getFusedLocationProviderClient(androidContext())
    }

    single { WeatherMapper() }

    single<LocationRepo> { LocationRepoImp(get(), get()) }
    single<WeatherRepo> { WeatherRepoImp(get(), get()) }

    single { GetCurrentLocationUseCase(get()) }
    single { GetWeatherByLocationUseCase(get()) }

    viewModel {
        WeatherViewModel(
            getCurrentLocationUseCase = get(),
            getWeatherByLocationUseCase = get()
        )
    }
}