package com.oxio.weather.screen

import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.oxio.weather.services.Resource
import com.oxio.weather.viewmodel.WeatherViewModel

@Composable
fun WeatherScreen(
    viewModel: WeatherViewModel,
    onNavigateToForecast: (String) -> Unit
) {
    var city by remember { mutableStateOf("") }
    val weather by viewModel.currentWeather.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = city,
            onValueChange = { city = it },
            label = { Text("Enter city") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Fetch Weather Button
        Button(onClick = { viewModel.fetchCurrentWeather(city) }) {
            Text("Get Weather")
        }

        Spacer(modifier = Modifier.height(24.dp))

        when (weather) {
            is Resource.Loading -> Text("Loading...", style = MaterialTheme.typography.headlineMedium)
            is Resource.Success -> {
                val data = (weather as Resource.Success).data
                data?.let {
                    Text("Weather: ${it.weather[0].description}", style = MaterialTheme.typography.bodyMedium)
                    Text("Temperature: ${it.main.temp}°C", style = MaterialTheme.typography.bodyMedium)
                    Text("Feels Like: ${it.main.feelsLike}°C", style = MaterialTheme.typography.bodyMedium)
                    Text("Humidity: ${it.main.humidity}%", style = MaterialTheme.typography.bodyMedium)
                    Text("Wind Speed: ${it.wind.speed} m/s", style = MaterialTheme.typography.bodyMedium)
                }
                Spacer(modifier = Modifier.height(16.dp))

                // Navigate to Forecast
                Button(onClick = { onNavigateToForecast(city) }) {
                    Text("View Forecast")
                }
            }
            is Resource.Error -> {
                val message = (weather as Resource.Error).message ?: "An error occurred"
                Text("Error: $message", color = Color.Red)
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = { viewModel.fetchCurrentWeather(city) }) {
                    Text("Retry")
                }
            }
        }
    }
}

