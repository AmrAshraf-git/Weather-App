package com.ipro.weatherapp.presentation.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ipro.weatherapp.presentation.ui.theme.DayThemeColor
import com.ipro.weatherapp.presentation.ui.theme.ThemeColor
import com.ipro.weatherapp.presentation.util.UiImage

@Composable
fun HourlyWeatherCard(
    image: UiImage,
    status: String,
    value: String,
    unit: String,
    time: String,
    theme: ThemeColor,
    modifier: Modifier = Modifier,
) {
    val backgroundColor = if (theme == DayThemeColor) {
        Color.White.copy(alpha = 0.7f)
    } else {
        theme.backgroundMainColor
    }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = modifier
                .padding(top = 12.dp)
                .clip(RoundedCornerShape(24.dp))
                .width(88.dp)
                .background(backgroundColor)
                .border(
                    width = 1.dp,
                    color = theme.textColor.copy(alpha = 0.08f),
                    shape = RoundedCornerShape(24.dp)
                )
                .padding(
                    top = 62.dp,
                    start = 8.dp,
                    bottom = 16.dp,
                    end = 8.dp
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "$value$unit",
                color = theme.textColor.copy(alpha = 0.87f),
                style = TextStyle(
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    letterSpacing = 0.25.sp,
                    textAlign = TextAlign.Center
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .padding(bottom = 4.dp)
            )

            Text(
                text = time,
                color = theme.textColor.copy(alpha = 0.6f),
                style = TextStyle(
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    letterSpacing = 0.25.sp,
                    textAlign = TextAlign.Center
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
            )
        }

        Image(
            painter = image.asPainter(),
            contentDescription = status,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .height(58.dp)
                .width(64.dp)
        )
    }
}
