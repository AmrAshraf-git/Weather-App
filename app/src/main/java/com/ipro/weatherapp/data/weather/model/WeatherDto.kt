package data.weather.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherDto(
    @SerialName("current")
    val currentWeather: CurrentWeather?,
//    @SerialName("current_units")
//    val currentWeatherUnits: CurrentWeatherUnits?,
    @SerialName("daily")
    val dailyWeather: DailyWeather?,
//    @SerialName("daily_units")
//    val dailyWeatherUnits: DailyWeatherUnits?,
//    @SerialName("elevation")
//    val elevation: Double?,
//    @SerialName("generationtime_ms")
//    val generationtimeMs: Double?,
    @SerialName("hourly")
    val hourlyWeather: HourlyWeather?,
//    @SerialName("hourly_units")
//    val hourlyWeatherUnits: HourlyWeatherUnits?,
//    @SerialName("latitude")
//    val latitude: Double?,
//    @SerialName("longitude")
//    val longitude: Double?,
//    @SerialName("timezone")
//    val timezone: String?,
//    @SerialName("timezone_abbreviation")
//    val timezoneAbbreviation: String?,
//    @SerialName("utc_offset_seconds")
//    val utcOffsetSeconds: Int?
)