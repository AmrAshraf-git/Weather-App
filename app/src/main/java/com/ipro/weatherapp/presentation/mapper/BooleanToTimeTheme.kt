package com.ipro.weatherapp.presentation.mapper

import com.ipro.weatherapp.presentation.model.TimeTheme

fun Boolean.toTimeTheme(): TimeTheme {
    return when (this) {
        true -> TimeTheme.DAY
        false -> TimeTheme.NIGHT
    }
}