package com.ipro.weatherapp

import com.ipro.weatherapp.domain.model.LocationCoordinate
import com.ipro.weatherapp.domain.usecase.GetCityNameFromLocationUseCase
import com.ipro.weatherapp.domain.usecase.GetCurrentLocationUseCase
import com.ipro.weatherapp.domain.usecase.GetWeatherByLocationUseCase
import com.ipro.weatherapp.presentation.components.WeatherEvent
import com.ipro.weatherapp.presentation.viewModel.WeatherViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class WeatherViewModelTest {

    private lateinit var getCurrentLocationUseCase: GetCurrentLocationUseCase
    private lateinit var getWeatherByLocationUseCase: GetWeatherByLocationUseCase
    private lateinit var getCityNameFromLocationUseCase: GetCityNameFromLocationUseCase
    private lateinit var viewModel: WeatherViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher()) // Coroutine test dispatcher
        getCurrentLocationUseCase = mockk()
        getWeatherByLocationUseCase = mockk()
        getCityNameFromLocationUseCase = mockk()
        viewModel = WeatherViewModel(getCurrentLocationUseCase
            , getWeatherByLocationUseCase
            , getCityNameFromLocationUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loadWeather emits success state`() = runTest {
        // Given
        val fakeLocation = LocationCoordinate(1.0, 2.0)
        val expectedWeather = MockWeatherFactory.mockWeather()
        val fakeCityName = "Cairo"

        coEvery { getCurrentLocationUseCase() } returns fakeLocation
        coEvery { getWeatherByLocationUseCase(fakeLocation) } returns expectedWeather
        coEvery { getCityNameFromLocationUseCase(fakeLocation) } returns fakeCityName

        // Given


        // When
        viewModel.onEvent(WeatherEvent.LoadWeather)
        advanceUntilIdle()

        // Then
        val state = viewModel.state.value

        assertEquals(false, state.isLoading)
        assertNull(state.error)
        assertEquals(expectedWeather.currentWeatherData, state.currentWeather)
        assertEquals(expectedWeather.hourlyWeatherData, state.hourlyTemperatureData)
        assertEquals(expectedWeather.dailyWeatherData, state.dailyWeatherData)
        assertEquals(fakeCityName, state.cityName)
        assertTrue(state.currentWeather?.isDay?:false)
    }

    @Test
    fun `loadWeather emits error state when location fails`() = runTest {
        coEvery { getCurrentLocationUseCase() } throws RuntimeException("Location error")

        viewModel.onEvent(WeatherEvent.LoadWeather)
        advanceUntilIdle()

        val state = viewModel.state.value
        assertFalse(state.isLoading)
        assertEquals("Location error", state.error)
        assertNull(state.currentWeather)
    }

    @Test
    fun `retry event re-triggers loadWeather`() = runTest {
        val fakeLocation = LocationCoordinate(1.0, 2.0)
        val fakeWeather = MockWeatherFactory.mockWeather()
        val fakeCityName = "Cairo"

        coEvery { getCurrentLocationUseCase() } returns fakeLocation
        coEvery { getWeatherByLocationUseCase(fakeLocation) } returns fakeWeather
        coEvery { getCityNameFromLocationUseCase(fakeLocation) } returns fakeCityName

        viewModel.onEvent(WeatherEvent.Retry("Location error"))
        advanceUntilIdle()

        val state = viewModel.state.value
        assertEquals(fakeWeather.currentWeatherData, state.currentWeather)
    }
}
