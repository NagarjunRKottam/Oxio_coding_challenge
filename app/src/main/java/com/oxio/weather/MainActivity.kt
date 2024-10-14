package com.oxio.weather

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.oxio.weather.screen.WeatherScreen
import com.oxio.weather.screen.ForecastWeatherScreen
import com.oxio.weather.screen.WelcomeScreen
import com.oxio.weather.viewmodel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val viewModel: WeatherViewModel = hiltViewModel()

            NavHost(navController = navController, startDestination = "welcome") {
                // Welcome Screen
                composable("welcome") {
                    WelcomeScreen(navController = navController)
                }

                // Current Weather Screen
                composable("current_weather") {
                    WeatherScreen(
                        viewModel = viewModel,
                        onNavigateToForecast = { city ->
                            navController.navigate("forecast/$city")
                        }
                    )
                }

                // Forecast Weather Screen
                composable(
                    "forecast/{city}",
                    arguments = listOf(navArgument("city") { type = NavType.StringType })
                ) { backStackEntry ->
                    val city = backStackEntry.arguments?.getString("city") ?: ""
                    ForecastWeatherScreen(viewModel = viewModel, city = city)
                }
            }
        }
    }
}
