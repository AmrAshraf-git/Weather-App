package com.ipro.weatherapp.di

import com.ipro.weatherapp.presentation.viewModel.WeatherViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val presentationModule = module {
    viewModelOf(::WeatherViewModel)
}