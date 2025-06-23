package data.weather.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CurrentWeatherDto(
    @SerialName("interval")
    val interval: Int?,

    @SerialName("temperature_2m")
    val temperature2m: Double?,

    @SerialName("time")
    val time: String?,

    @SerialName("weather_code")
    val weatherCode: Int?,

    @SerialName("apparent_temperature")
    val apparentTemperature: Double?,

    @SerialName("wind_speed_10m")
    val windSpeed10m: Double?,

    @SerialName("relative_humidity_2m")
    val relativeHumidity2m: Int?,

    @SerialName("uv_index")
    val uvIndex: Double?,

    @SerialName("surface_pressure")
    val surfacePressure: Double?,

    @SerialName("is_day")
    val isDay: Int?,

    @SerialName("precipitation_probability")
    val precipitationProbability: Double?,
)