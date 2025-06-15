package com.ipro.weatherapp.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ipro.weatherapp.domain.exception.LocationUnavailableException
import com.ipro.weatherapp.domain.exception.NoWeatherFoundException
import com.ipro.weatherapp.domain.usecase.GetCityNameFromLocationUseCase
import com.ipro.weatherapp.domain.usecase.GetCurrentLocationUseCase
import com.ipro.weatherapp.domain.usecase.GetWeatherByLocationUseCase
import com.ipro.weatherapp.presentation.components.WeatherEvent
import com.ipro.weatherapp.presentation.mapper.toTimeTheme
import com.ipro.weatherapp.presentation.mapper.toUiModel
import com.ipro.weatherapp.presentation.model.WeatherState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class WeatherViewModel(
    private val getCurrentLocationUseCase: GetCurrentLocationUseCase,
    private val getWeatherByLocationUseCase: GetWeatherByLocationUseCase,
    private val getCityNameFromLocationUseCase: GetCityNameFromLocationUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(WeatherState())
    val state = _state.asStateFlow()

    fun onEvent(event: WeatherEvent) {
        when (event) {
            is WeatherEvent.LoadWeather -> {
                loadWeather()
            }

            is WeatherEvent.Retry -> loadWeather()
        }
    }

    private fun loadWeather() {
        viewModelScope.launch {
            _state.value = state.value.copy(isLoading = true)
            try {
                val location = getCurrentLocationUseCase()
                val cityName = getCityNameFromLocationUseCase(location)
                val weather = getWeatherByLocationUseCase(location)

                val timeTheme = weather.current.isDay.toTimeTheme()

                _state.value = state.value.copy(
                    currentWeather = weather.current.toUiModel(),
                    hourlyTemperatureData = weather.hourlyTemperatureData.map { it.toUiModel(timeTheme) },
                    dailyWeatherData = weather.dailyWeatherData.map { it.toUiModel(timeTheme) },
                    isLoading = false,
                    error = null,
                    cityName = cityName
                )
            } catch (e: LocationUnavailableException) {
                _state.value = state.value.copy(
                    isLoading = false,
                    error = "Unable to get location"
                )
            } catch (e: NoWeatherFoundException) {
                _state.value = state.value.copy(
                    isLoading = false,
                    error = "No weather data found"
                )
            } catch (e: Exception) {
                _state.value = state.value.copy(
                    isLoading = false,
                    error = e.message ?: "Unknown error"
                )
            }
        }
    }
}