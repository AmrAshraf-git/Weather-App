package com.ipro.weatherapp.presentation.ui.screen

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ipro.weatherapp.R
import com.ipro.weatherapp.presentation.components.WeatherEvent
import com.ipro.weatherapp.presentation.model.CurrentWeatherUiData
import com.ipro.weatherapp.presentation.model.DailyWeatherUiData
import com.ipro.weatherapp.presentation.model.HourlyTemperatureUiData
import com.ipro.weatherapp.presentation.model.WeatherUi
import com.ipro.weatherapp.presentation.model.WeatherUiState
import com.ipro.weatherapp.presentation.ui.composables.HourlyWeatherCard
import com.ipro.weatherapp.presentation.ui.composables.WeatherDetail
import com.ipro.weatherapp.presentation.ui.theme.DayThemeColor
import com.ipro.weatherapp.presentation.ui.theme.WeatherAppTheme
import com.ipro.weatherapp.presentation.util.UiImage
import com.ipro.weatherapp.presentation.util.UiText
import com.ipro.weatherapp.presentation.viewModel.WeatherViewModel
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDate

const val TRANSITION_DISTANCE = 5f
const val ANIMATION_DURATION = 300

@Composable
fun WeatherScreen(
    modifier: Modifier = Modifier,
    viewModel: WeatherViewModel = koinViewModel()) {
    val state by viewModel.state.collectAsState()

    // Trigger weather load once
    LaunchedEffect(Unit) {
        viewModel.onEvent(WeatherEvent.LoadWeather)
    }
    WeatherContent(state = state, modifier = modifier)
}


@Composable
private fun WeatherContent(
    modifier: Modifier = Modifier,
    state: WeatherUiState
) {
    val listState = rememberLazyListState()
    val scrollOffset = remember { derivedStateOf { listState.firstVisibleItemScrollOffset } }
    val progress = (scrollOffset.value / 200f).coerceIn(0f, 1f)

    if (state.isLoading) {
        Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    Column(modifier = Modifier.fillMaxSize().background(
        Brush.verticalGradient(
            colors = listOf(
                Color(0xFFB9E5FE),
                Color(0xFFFFFFFF)
            )
        )
    )) {
        state.weather?.let { weather ->
            LazyColumn(
                state = listState,
                modifier = modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFFB9E5FE),
                                Color(0xFFFFFFFF)
                            )
                        )
                    )
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    WeatherHeader(
                        current = weather.current,
                        cityName = state.cityName,
                        maxTemp = weather.dailyForecasts.first().maxTemp,
                        minTemp = weather.dailyForecasts.first().minTemp,
                        progress = progress
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    CurrentDetails(weather.current)
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        "Today", style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Start,
                        fontWeight = FontWeight.Bold
                    )
                    LazyRow {
                        items(weather.hourlyTemperatures) { item ->
                            HourlyWeatherCard(
                                image = item.weatherImage,
                                status = "",
                                value = item.temperature.toString(),
                                unit = "°C",
                                time = "${item.hour}:00",
                                theme = DayThemeColor
                            )
                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(
                        "Next 7 days", style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Start,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    weather.dailyForecasts.forEach {
                        DailyItem(it)
                    }
                }
            }
        } ?: run {
            Text(text = state.error ?: "No data", modifier = Modifier.padding(16.dp))
        }
    }

}

@Composable
fun WeatherHeader(
    current: CurrentWeatherUiData,
    cityName: String,
    maxTemp: Int,
    minTemp: Int,
    progress: Float
) {
    val isCollapsed = progress > 0.5f

    val iconScale by animateFloatAsState(targetValue = 1f - 0.4f * progress)
    val textScale by animateFloatAsState(targetValue = 1f - 0.1f * progress)

    val iconOffsetX by animateDpAsState(targetValue = (-80).dp * progress)
    val textOffsetX by animateDpAsState(targetValue = (10).dp * progress)

    val topPadding by animateDpAsState(targetValue = 32.dp - 16.dp * progress)
    val imageSize by animateDpAsState(targetValue = 200.dp - 10.dp * progress)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = topPadding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Location Row
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(R.drawable.ic_location),
                contentDescription = null,
                tint = Color.Black,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(cityName, style = MaterialTheme.typography.titleMedium)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Weather Content (Box wraps dynamic layout)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp),
            contentAlignment = Alignment.Center
        ) {
            if (isCollapsed) {
                // Collapsed State: Row layout
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(horizontal = 8.dp)
                ) {
                    Image(
                        painter = current.weatherImage.asPainter(),
                        contentDescription = null,
                        modifier = Modifier
                            .size(imageSize)
                            .scale(iconScale)
                            .offset(x = iconOffsetX)
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    Column(
                        horizontalAlignment = Alignment.Start,
                        modifier = Modifier
                            .scale(textScale)
                            .offset(x = textOffsetX)
                    ) {
                        Text(
                            text = "${current.temperature}°C",
                            style = MaterialTheme.typography.displayLarge
                        )
                        Text(
                            text = current.weatherCondition.asString(),
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            modifier = Modifier
                                .clip(RoundedCornerShape(50))
                                .background(Color(0x336E7FFF))
                                .padding(horizontal = 20.dp, vertical = 10.dp)
                        ) {
                            Text("↑ $maxTemp°C", modifier = Modifier.padding(end = 8.dp))
                            Text("↓ $minTemp°C")
                        }
                    }
                }
            } else {
                // Expanded State: Column layout
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.align(Alignment.Center)
                ) {
                    Image(
                        painter = current.weatherImage.asPainter(),
                        contentDescription = null,
                        modifier = Modifier
                            .size(imageSize)
                            .scale(iconScale)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "${current.temperature}°C",
                        style = MaterialTheme.typography.displayLarge
                    )
                    Text(
                        text = current.weatherCondition.asString(),
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(50))
                            .background(Color(0x336E7FFF))
                            .padding(horizontal = 20.dp, vertical = 10.dp)
                    ) {
                        Text("↑ $maxTemp°C", modifier = Modifier.padding(end = 8.dp))
                        Text("↓ $minTemp°C")
                    }
                }
            }
        }
    }
}

@Composable
fun CurrentDetails(data: CurrentWeatherUiData) {
    Column(
        Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.End) {
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp, alignment = Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()) {
            WeatherDetail("Wind", "${data.windSpeed} KM/h", R.drawable.ic_fast_wind)
            WeatherDetail("Humidity", "${data.humidity}%", R.drawable.ic_humidity)
            WeatherDetail("Rain", "${data.rain}%", R.drawable.ic_rain)
        }
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp, alignment = Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()) {
            WeatherDetail("UV", data.uvIndex.toString(), R.drawable.ic_uv_index)
            WeatherDetail("Pressure", "${data.pressure} hPa", R.drawable.ic_arrow_down)
            WeatherDetail("Feels like", "${data.feelsLike}°C", R.drawable.ic_temperature)
        }
    }
}



@Composable
fun DailyItem(data: DailyWeatherUiData) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(data.date.dayOfWeek.name.lowercase().replaceFirstChar(Char::uppercaseChar))
        Image(
            painter = data.weatherImage.asPainter(),
            contentDescription = null,
            modifier = Modifier.size(32.dp)
        )
        Text("↑ ${data.maxTemp}°C")
        Text("↓ ${data.minTemp}°C")
    }
}


@Preview(showBackground = true)
@Composable
private fun PreviewWeatherScreenNight() {
    val dummyState = WeatherUiState(
        isLoading = false,
        cityName = "Baghdad",
        weather = WeatherUi(
            current = CurrentWeatherUiData(
                temperature = 24,
                feelsLike = 22,
                windSpeed = 13,
                humidity = 24,
                uvIndex = 2,
                pressure = 1012,
                isDay = false,
                rain = 2,
                weatherCondition = UiText.DynamicString("Partly cloudy"),
                weatherImage = UiImage.Drawable(R.drawable.light_partialy_cloudy)
            ),
            hourlyTemperatures = listOf(
                HourlyTemperatureUiData(
                    25,
                    11,
                    UiText.DynamicString("Partly cloudy"),
                    UiImage.Drawable(R.drawable.light_partialy_cloudy)
                ),
                HourlyTemperatureUiData(
                    25,
                    12,
                    UiText.DynamicString("Sunny"),
                    UiImage.Drawable(R.drawable.light_clear_sky)
                ),
                HourlyTemperatureUiData(
                    25,
                    13,
                    UiText.DynamicString("Sunny"),
                    UiImage.Drawable(R.drawable.light_clear_sky)
                )
            ),
            dailyForecasts = listOf(
                DailyWeatherUiData(
                    LocalDate.now(),
                    32,
                    20,
                    UiText.DynamicString("Sunny"),
                    UiImage.Drawable(R.drawable.light_clear_sky)
                ),
                DailyWeatherUiData(
                    LocalDate.now().plusDays(1),
                    32,
                    20,
                    UiText.DynamicString("Partly cloudy"),
                    UiImage.Drawable(R.drawable.light_partialy_cloudy)
                ),
                DailyWeatherUiData(
                    LocalDate.now().plusDays(2),
                    32,
                    20,
                    UiText.DynamicString("Partly cloudy"),
                    UiImage.Drawable(R.drawable.light_partialy_cloudy)
                ),
                DailyWeatherUiData(
                    LocalDate.now().plusDays(3),
                    32,
                    20,
                    UiText.DynamicString("Sunny"),
                    UiImage.Drawable(R.drawable.light_clear_sky)
                ),
                DailyWeatherUiData(
                    LocalDate.now().plusDays(4),
                    32,
                    20,
                    UiText.DynamicString("Cloudy"),
                    UiImage.Drawable(R.drawable.light_overcast)
                ),
                DailyWeatherUiData(
                    LocalDate.now().plusDays(5),
                    32,
                    20,
                    UiText.DynamicString("Sunny"),
                    UiImage.Drawable(R.drawable.light_clear_sky)
                ),
                DailyWeatherUiData(
                    LocalDate.now().plusDays(6),
                    32,
                    20,
                    UiText.DynamicString("Cloudy"),
                    UiImage.Drawable(R.drawable.light_mainly_clear)
                )
            )
        )
    )

    WeatherContent(state = dummyState)

    WeatherAppTheme {
        WeatherContent(
            state = dummyState
        )
    }
}