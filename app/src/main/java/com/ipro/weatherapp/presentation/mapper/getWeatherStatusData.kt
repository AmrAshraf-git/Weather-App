package com.ipro.weatherapp.presentation.mapper

import com.ipro.weatherapp.R
import com.ipro.weatherapp.domain.model.WeatherCondition
import com.ipro.weatherapp.presentation.model.TimeTheme
import com.ipro.weatherapp.presentation.util.UiImage
import com.ipro.weatherapp.presentation.util.UiText

fun WeatherCondition.getWeatherImage(timeTheme: TimeTheme): UiImage {
    return if (timeTheme == TimeTheme.DAY) {
        UiImage.Drawable(
            id = when (this) {
                WeatherCondition.CLEAR_SKY -> R.drawable.light_clear_sky
                WeatherCondition.MAINLY_CLEAR -> R.drawable.light_mainly_clear
                WeatherCondition.PARTLY_CLOUDY -> R.drawable.light_partialy_cloudy
                WeatherCondition.OVERCAST -> R.drawable.light_overcast
                WeatherCondition.FOG -> R.drawable.light_fog
                WeatherCondition.DEPOSITING_RIME_FOG -> R.drawable.light_rime_fog
                WeatherCondition.LIGHT_DRIZZLE -> R.drawable.light_drizzle_light
                WeatherCondition.MODERATE_DRIZZLE -> R.drawable.light_drizzle_moderate
                WeatherCondition.DENSE_DRIZZLE -> R.drawable.light_drizzle_intensity
                WeatherCondition.LIGHT_FREEZING_DRIZZLE -> R.drawable.light_freezing_drizzle_light
                WeatherCondition.DENSE_FREEZING_DRIZZLE -> R.drawable.light_freezing_drizzle_intensity
                WeatherCondition.SLIGHT_RAIN -> R.drawable.light_rain_slight
                WeatherCondition.MODERATE_RAIN -> R.drawable.light_rain_moderate
                WeatherCondition.HEAVY_RAIN -> R.drawable.light_rain_intensity
                WeatherCondition.LIGHT_FREEZING_RAIN -> R.drawable.light_freezing_rain_light
                WeatherCondition.HEAVY_FREEZING_RAIN -> R.drawable.light_freezing_rain_heavy
                WeatherCondition.SLIGHT_SNOW_FALL -> R.drawable.light_snow_fall_light
                WeatherCondition.MODERATE_SNOW_FALL -> R.drawable.light_snow_fall_moderate
                WeatherCondition.HEAVY_SNOW_FALL -> R.drawable.light_snowfall_heavy
                WeatherCondition.SNOW_GRAINS -> R.drawable.light_snow_grains
                WeatherCondition.SLIGHT_RAIN_SHOWERS -> R.drawable.light_rain_shower_slight
                WeatherCondition.MODERATE_RAIN_SHOWERS -> R.drawable.light_rain_shower_moderate
                WeatherCondition.VIOLENT_RAIN_SHOWERS -> R.drawable.light_rain_shower_violent
                WeatherCondition.SLIGHT_SNOW_SHOWERS -> R.drawable.light_snow_shower_slight
                WeatherCondition.HEAVY_SNOW_SHOWERS -> R.drawable.light_snow_shower_heavy
                WeatherCondition.SLIGHT_OR_MODERATE_THUNDERSTORM -> R.drawable.light_thunderstorm_moderate
                WeatherCondition.THUNDERSTORM_WITH_SLIGHT_HAIL -> R.drawable.light_thunderstorm_with_slight_hail
                WeatherCondition.THUNDERSTORM_WITH_HEAVY_HAIL -> R.drawable.light_thunderstrom_with_heavy_hail
                WeatherCondition.UNKNOWN_WEATHER_FORECAST -> R.drawable.light_clear_sky
            }
        )
    } else {
        UiImage.Drawable(
            id = when (this) {
                WeatherCondition.CLEAR_SKY -> R.drawable.night_clear_sky
                WeatherCondition.MAINLY_CLEAR -> R.drawable.night_mainly_clear
                WeatherCondition.PARTLY_CLOUDY -> R.drawable.night_partly_cloudy
                WeatherCondition.OVERCAST -> R.drawable.night_overcast
                WeatherCondition.FOG -> R.drawable.night_fog
                WeatherCondition.DEPOSITING_RIME_FOG -> R.drawable.night_rime_fog
                WeatherCondition.LIGHT_DRIZZLE -> R.drawable.night_drizzle_light
                WeatherCondition.MODERATE_DRIZZLE -> R.drawable.night_drizzle_moderate
                WeatherCondition.DENSE_DRIZZLE -> R.drawable.night_drizzle_intensity
                WeatherCondition.LIGHT_FREEZING_DRIZZLE -> R.drawable.night_freezing_drizzle_light
                WeatherCondition.DENSE_FREEZING_DRIZZLE -> R.drawable.night_freezing_drizzle_intensity
                WeatherCondition.SLIGHT_RAIN -> R.drawable.night_rain_slight
                WeatherCondition.MODERATE_RAIN -> R.drawable.night_rain_moderate
                WeatherCondition.HEAVY_RAIN -> R.drawable.night_rain_intensity
                WeatherCondition.LIGHT_FREEZING_RAIN -> R.drawable.night_freezing_rain_light
                WeatherCondition.HEAVY_FREEZING_RAIN -> R.drawable.night_freezing_rain_heavy
                WeatherCondition.SLIGHT_SNOW_FALL -> R.drawable.night_snowfall_slight
                WeatherCondition.MODERATE_SNOW_FALL -> R.drawable.night_snowfall_moderate
                WeatherCondition.HEAVY_SNOW_FALL -> R.drawable.night_snowfall_heavy
                WeatherCondition.SNOW_GRAINS -> R.drawable.night_snow_grains
                WeatherCondition.SLIGHT_RAIN_SHOWERS -> R.drawable.night_rain_shower_slight
                WeatherCondition.MODERATE_RAIN_SHOWERS -> R.drawable.night_rain_shower_moderate
                WeatherCondition.VIOLENT_RAIN_SHOWERS -> R.drawable.night_rain_shower_violent
                WeatherCondition.SLIGHT_SNOW_SHOWERS -> R.drawable.night_snow_shower_slight
                WeatherCondition.HEAVY_SNOW_SHOWERS -> R.drawable.night_snow_shower_heavy
                WeatherCondition.SLIGHT_OR_MODERATE_THUNDERSTORM -> R.drawable.night_thunderstorm_moderate
                WeatherCondition.THUNDERSTORM_WITH_SLIGHT_HAIL -> R.drawable.night_thunderstorm_with_slight_hail
                WeatherCondition.THUNDERSTORM_WITH_HEAVY_HAIL -> R.drawable.night_thunderstrom_with_heavy_hail
                WeatherCondition.UNKNOWN_WEATHER_FORECAST -> R.drawable.light_clear_sky
            }
        )
    }
}

fun WeatherCondition.getWeatherDescription(): UiText {
    return UiText.StringResource(
        id = when (this) {
            WeatherCondition.CLEAR_SKY -> R.string.clear_sky
            WeatherCondition.MAINLY_CLEAR -> R.string.mainly_clear
            WeatherCondition.PARTLY_CLOUDY -> R.string.partly_cloudy
            WeatherCondition.OVERCAST -> R.string.overcast
            WeatherCondition.FOG -> R.string.fog
            WeatherCondition.DEPOSITING_RIME_FOG -> R.string.depositing_rime_fog
            WeatherCondition.LIGHT_DRIZZLE -> R.string.light_drizzle
            WeatherCondition.MODERATE_DRIZZLE -> R.string.moderate_drizzle
            WeatherCondition.DENSE_DRIZZLE -> R.string.dense_drizzle
            WeatherCondition.LIGHT_FREEZING_DRIZZLE -> R.string.light_freezing_drizzle
            WeatherCondition.DENSE_FREEZING_DRIZZLE -> R.string.dense_freezing_drizzle
            WeatherCondition.SLIGHT_RAIN -> R.string.slight_rain
            WeatherCondition.MODERATE_RAIN -> R.string.moderate_rain
            WeatherCondition.HEAVY_RAIN -> R.string.heavy_rain
            WeatherCondition.LIGHT_FREEZING_RAIN -> R.string.light_freezing_rain
            WeatherCondition.HEAVY_FREEZING_RAIN -> R.string.heavy_freezing_rain
            WeatherCondition.SLIGHT_SNOW_FALL -> R.string.slight_snow_fall
            WeatherCondition.MODERATE_SNOW_FALL -> R.string.moderate_snow_fall
            WeatherCondition.HEAVY_SNOW_FALL -> R.string.heavy_snow_fall
            WeatherCondition.SNOW_GRAINS -> R.string.snow_grains
            WeatherCondition.SLIGHT_RAIN_SHOWERS -> R.string.slight_rain_showers
            WeatherCondition.MODERATE_RAIN_SHOWERS -> R.string.moderate_rain_showers
            WeatherCondition.VIOLENT_RAIN_SHOWERS -> R.string.violent_rain_showers
            WeatherCondition.SLIGHT_SNOW_SHOWERS -> R.string.slight_snow_showers
            WeatherCondition.HEAVY_SNOW_SHOWERS -> R.string.heavy_snow_showers
            WeatherCondition.SLIGHT_OR_MODERATE_THUNDERSTORM -> R.string.slight_or_moderate_thunderstorm
            WeatherCondition.THUNDERSTORM_WITH_SLIGHT_HAIL -> R.string.thunderstorm_with_slight_hail
            WeatherCondition.THUNDERSTORM_WITH_HEAVY_HAIL -> R.string.thunderstorm_with_heavy_hail
            WeatherCondition.UNKNOWN_WEATHER_FORECAST -> R.string.unknown_condition
        }
    )
}