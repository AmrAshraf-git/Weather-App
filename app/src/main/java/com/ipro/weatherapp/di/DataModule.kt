package com.ipro.weatherapp.di

import com.google.android.gms.location.LocationServices
import com.ipro.weatherapp.data.location.repository.LocationRepoImp
import com.ipro.weatherapp.data.weather.mapper.WeatherMapper
import com.ipro.weatherapp.data.weather.remote.WeatherRemoteDataSource
import com.ipro.weatherapp.data.weather.remote.WeatherRemoteDataSourceImp
import com.ipro.weatherapp.data.weather.repository.WeatherRepoImp
import com.ipro.weatherapp.domain.repository.LocationRepo
import com.ipro.weatherapp.domain.repository.WeatherRepo
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module



val dataModule = module {

    single {
        LocationServices.getFusedLocationProviderClient(androidContext())
    }

    single<LocationRepo> {
        LocationRepoImp(
            context = androidContext(),
            fusedLocationProviderClient = get()
        )
    }

    single { WeatherMapper }

    single<WeatherRemoteDataSource> {
        WeatherRemoteDataSourceImp(client = get(), json = get())
    }

    single<WeatherRepo> { WeatherRepoImp(weatherMapper = get(),
        weatherRemoteDataSource = get()) }


    single {
        Json {
            ignoreUnknownKeys = true
            isLenient = true
        }
    }

    single<HttpClient> {
        HttpClient(OkHttp) {
            install(ContentNegotiation) {
                json(get())
            }
            install(HttpTimeout) {
                requestTimeoutMillis = 15000
                connectTimeoutMillis = 15000
                socketTimeoutMillis = 15000
            }
        }
    }


}