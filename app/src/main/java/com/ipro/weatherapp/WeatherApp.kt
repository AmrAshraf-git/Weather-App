package com.ipro.weatherapp

import android.app.Application
import com.ipro.weatherapp.di.dataModule
import com.ipro.weatherapp.di.domainModule
import com.ipro.weatherapp.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class WeatherApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@WeatherApp)
            modules(dataModule, domainModule, presentationModule)
        }
    }
}