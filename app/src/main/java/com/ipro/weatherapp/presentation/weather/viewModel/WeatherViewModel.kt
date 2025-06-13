package com.ipro.weatherapp.presentation.weather.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ipro.weatherapp.domain.usecase.GetCurrentLocationUseCase
import com.ipro.weatherapp.domain.usecase.GetWeatherByLocationUseCase
import com.ipro.weatherapp.presentation.weather.WeatherState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WeatherViewModel(private val getCurrentLocationUseCase: GetCurrentLocationUseCase,
                       private val getWeatherByLocationUseCase: GetWeatherByLocationUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(WeatherState())
    val state: StateFlow<WeatherState> = _state

    fun loadWeather() {
        _state.value = _state.value.copy(isLoading = true, errorMessage = null)

        viewModelScope.launch {
            val locationResult = getCurrentLocationUseCase()
            locationResult
                .onSuccess { location ->
                    getWeatherByLocationUseCase(location)
                        .onSuccess { weather ->
                            _state.value = WeatherState(
                                isLoading = false,
                                weather = weather
                            )
                        }
                        .onFailure { e ->
                            _state.value = WeatherState(
                                isLoading = false,
                                errorMessage = e.message ?: "Unknown weather error"
                            )
                        }
                }
                .onFailure { e ->
                    _state.value = WeatherState(
                        isLoading = false,
                        errorMessage = e.message ?: "Failed to get location"
                    )
                }
        }
    }
}