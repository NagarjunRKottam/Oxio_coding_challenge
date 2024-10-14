package com.oxio.weather.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.oxio.weather.data.ForecastItem
import com.oxio.weather.services.Resource
import com.oxio.weather.viewmodel.WeatherViewModel
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun ForecastWeatherScreen(
    viewModel: WeatherViewModel,
    city: String
) {
    LaunchedEffect(city) {
        viewModel.fetchForecast(city)
    }

    val forecast by viewModel.forecast.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Weather Forecast for $city", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(16.dp))

        when (forecast) {
            is Resource.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }
            is Resource.Success -> {
                val data = (forecast as Resource.Success).data
                data?.let {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(vertical = 8.dp)
                    ) {
                        items(it.list) { item ->
                            ForecastItem(forecastItem = item)
                            Divider()
                        }
                    }
                }
            }
            is Resource.Error -> {
                val message = (forecast as Resource.Error).message ?: "An error occurred"
                Text("Error: $message", color = Color.Red)
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = { viewModel.fetchForecast(city) },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text("Retry")
                }
            }
        }
    }
}

@Composable
fun ForecastItem(forecastItem: ForecastItem) {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    val displayDateFormat = SimpleDateFormat("EEE, MMM d, h:mm a", Locale.getDefault())

    val date = dateFormat.parse(forecastItem.dateText)
    val formattedDate = date?.let { displayDateFormat.format(it) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text("Date: $formattedDate", style = MaterialTheme.typography.bodyLarge)
        Text("Weather: ${forecastItem.weather[0].description}", style = MaterialTheme.typography.bodyMedium)
        Text("Temperature: ${forecastItem.main.temp}°C", style = MaterialTheme.typography.bodyMedium)
        Text("Feels Like: ${forecastItem.main.feelsLike}°C", style = MaterialTheme.typography.bodyMedium)
        Text("Humidity: ${forecastItem.main.humidity}%", style = MaterialTheme.typography.bodyMedium)
    }
}
