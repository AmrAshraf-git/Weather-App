package data.weather.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DailyWeatherUnits(
    @SerialName("temperature_2m_max")
    val temperature2mMax: String?,
    @SerialName("temperature_2m_min")
    val temperature2mMin: String?,
    @SerialName("time")
    val time: String?,
    @SerialName("weather_code")
    val weatherCode: String?
)