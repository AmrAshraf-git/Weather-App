package data.weather.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherResponse(
    @SerialName("current")
    val currentWeatherDto: CurrentWeatherDto?,
    @SerialName("daily")
    val dailyWeatherDto: DailyWeatherDto?,
    @SerialName("hourly")
    val hourlyWeatherDto: HourlyWeatherDto?,
)