import com.ipro.weatherapp.domain.model.*
import java.time.LocalDate

object MockWeatherFactory {
    fun mockWeather(): Weather {
        return Weather(
            hourlyTemperatureData = mockHourlyTemperatures(),
            current = mockCurrentWeatherData(),
            dailyWeatherData = mockDailyForecasts()
        )
    }

    private fun mockCurrentWeatherData(): CurrentWeatherData {
        return CurrentWeatherData(
            temperature = 22,
            feelsLike = 20,
            windSpeed = 10,
            humidity = 60,
            uvIndex = 5,
            pressure = 1012,
            weatherCondition = WeatherCondition.CLEAR_SKY,
            isDay =true
        )
    }

    private fun mockDailyForecasts(): List<DailyWeatherData> {
        return listOf(
            DailyWeatherData(
                date = LocalDate.now(),
                maxTemp = 26,
                minTemp = 16,
                weatherCondition = WeatherCondition.CLEAR_SKY
            ),
            DailyWeatherData(
                date = LocalDate.now().plusDays(1),
                maxTemp = 24,
                minTemp = 15,
                weatherCondition = WeatherCondition.PARTLY_CLOUDY
            ),
            DailyWeatherData(
                date = LocalDate.now().plusDays(2),
                maxTemp = 21,
                minTemp = 14,
                weatherCondition = WeatherCondition.RAIN_SHOWER_MODRATE
            )
        )
    }

    private fun mockHourlyTemperatures(): List<HourlyTemperatureData> {
        return (0..23).map { hour ->
            HourlyTemperatureData(
                hour = hour,
                temperature = 15 + hour % 5 // predictable values
            )
        }
    }
}
