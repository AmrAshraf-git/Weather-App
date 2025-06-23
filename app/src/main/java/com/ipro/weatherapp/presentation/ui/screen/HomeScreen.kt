package com.ipro.weatherapp.presentation.ui.screen

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ipro.weatherapp.R
import com.ipro.weatherapp.presentation.components.WeatherEvent
import com.ipro.weatherapp.presentation.model.CurrentWeatherUiData
import com.ipro.weatherapp.presentation.model.DailyWeatherUiData
import com.ipro.weatherapp.presentation.model.HourlyTemperatureUiData
import com.ipro.weatherapp.presentation.model.WeatherUi
import com.ipro.weatherapp.presentation.model.WeatherUiState
import com.ipro.weatherapp.presentation.ui.theme.WeatherAppTheme
import com.ipro.weatherapp.presentation.util.UiImage
import com.ipro.weatherapp.presentation.util.UiText
import com.ipro.weatherapp.presentation.viewModel.WeatherViewModel
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDate

const val TRANSITION_DISTANCE = 5f
const val ANIMATION_DURATION = 300

@Composable
fun WeatherScreen(viewModel: WeatherViewModel = koinViewModel()) {
    val state by viewModel.state.collectAsState()

    // Trigger weather load once
    LaunchedEffect(Unit) {
        viewModel.onEvent(WeatherEvent.LoadWeather)
    }
    WeatherContent(state = state)
}






@Composable
private fun WeatherContent(
    state: WeatherUiState
) {
    if (state.isLoading) {
        CircularProgressIndicator(modifier = Modifier.fillMaxSize())
        return
    }
    state.weather?.let { weather ->
        LazyColumn(
            modifier = Modifier
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
                Text(
                    text = state.cityName,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(top = 24.dp)
                )
                Text(
                    text = "${weather.current.temperature}°C",
                    style = MaterialTheme.typography.displayLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = weather.current.weatherCondition.asString(),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(50))
                        .background(Color(0x336E7FFF))
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text(
                        "↑ ${weather.dailyForecasts.first().maxTemp}°C",
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Text("↓ ${weather.dailyForecasts.first().minTemp}°C")
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
                CurrentDetails(weather.current)
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
                Text("Today", style = MaterialTheme.typography.titleMedium)
                LazyRow {
                    items(weather.hourlyTemperatures) {
                        HourItem(it)
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
                Text("Next 7 days", style = MaterialTheme.typography.titleMedium)
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



@Composable
fun CurrentDetails(data: CurrentWeatherUiData) {
    Column {
        Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
            WeatherDetail("Wind", "${data.windSpeed} KM/h", R.drawable.ic_fast_wind)
            WeatherDetail("Humidity", "${data.humidity}%", R.drawable.ic_humidity)
            WeatherDetail("Rain", "${data.rain}%", R.drawable.ic_rain)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
            WeatherDetail("UV", data.uvIndex.toString(), R.drawable.ic_uv_index)
            WeatherDetail("Pressure", "${data.pressure} hPa", R.drawable.light_snow_grains)
            WeatherDetail("Feels like", "${data.feelsLike}°C", R.drawable.ic_fast_wind)
        }
    }
}

@Composable
fun WeatherDetail(label: String, value: String, @DrawableRes icon: Int) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .padding(12.dp)
            .width(100.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(painter = painterResource(icon), contentDescription = label)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = value, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
        Text(text = label, style = MaterialTheme.typography.bodySmall)
    }
}

@Composable
fun HourItem(data: HourlyTemperatureUiData) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(painter = data.weatherImage.asPainter(), contentDescription = null, modifier = Modifier.size(40.dp))
        Spacer(modifier = Modifier.height(4.dp))
        Text("${data.temperature}°C")
        Text("${data.hour}:00")
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
        Image(painter = data.weatherImage.asPainter(), contentDescription = null, modifier = Modifier.size(32.dp))
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
                HourlyTemperatureUiData(25, 11, UiText.DynamicString("Partly cloudy"), UiImage.Drawable(R.drawable.light_partialy_cloudy)),
                HourlyTemperatureUiData(25, 12, UiText.DynamicString("Sunny"), UiImage.Drawable(R.drawable.light_clear_sky)),
                HourlyTemperatureUiData(25, 13, UiText.DynamicString("Sunny"), UiImage.Drawable(R.drawable.light_clear_sky))
            ),
            dailyForecasts = listOf(
                DailyWeatherUiData(LocalDate.now(), 32, 20, UiText.DynamicString("Sunny"), UiImage.Drawable(R.drawable.light_clear_sky)),
                DailyWeatherUiData(LocalDate.now().plusDays(1), 32, 20, UiText.DynamicString("Partly cloudy"), UiImage.Drawable(R.drawable.light_partialy_cloudy)),
                DailyWeatherUiData(LocalDate.now().plusDays(2), 32, 20, UiText.DynamicString("Partly cloudy"), UiImage.Drawable(R.drawable.light_partialy_cloudy)),
                DailyWeatherUiData(LocalDate.now().plusDays(3), 32, 20, UiText.DynamicString("Sunny"), UiImage.Drawable(R.drawable.light_clear_sky)),
                DailyWeatherUiData(LocalDate.now().plusDays(4), 32, 20, UiText.DynamicString("Cloudy"), UiImage.Drawable(R.drawable.light_overcast)),
                DailyWeatherUiData(LocalDate.now().plusDays(5), 32, 20, UiText.DynamicString("Sunny"), UiImage.Drawable(R.drawable.light_clear_sky)),
                DailyWeatherUiData(LocalDate.now().plusDays(6), 32, 20, UiText.DynamicString("Cloudy"), UiImage.Drawable(R.drawable.light_mainly_clear))
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