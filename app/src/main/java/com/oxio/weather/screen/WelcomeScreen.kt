package com.oxio.weather.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.oxio.weather.usecases.PreferencesHelper

@Composable
fun WelcomeScreen(navController: NavController) {
    val context = LocalContext.current


    val showWelcome by remember { mutableStateOf(PreferencesHelper.isFirstLaunch(context)) }

    LaunchedEffect(showWelcome) {
        if (!showWelcome) {
            navController.navigate("current_weather") {
                popUpTo("welcome") { inclusive = true }
            }
        }
    }

    if (showWelcome) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Welcome to the Weather App!", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(24.dp))

            Button(onClick = {
                PreferencesHelper.setFirstLaunch(context, false)
                navController.navigate("current_weather") {
                    popUpTo("welcome") { inclusive = true }
                }
            }) {
                Text("Get Started")
            }
        }
    }
}
