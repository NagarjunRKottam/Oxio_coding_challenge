package com.oxio.weather.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oxio.weather.data.WeatherResponse
import com.oxio.weather.data.ForecastResponse
import com.oxio.weather.services.Resource
import com.oxio.weather.services.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository
) : ViewModel() {

    private val _currentWeather = MutableStateFlow<Resource<WeatherResponse>>(Resource.Loading())
    val currentWeather: StateFlow<Resource<WeatherResponse>> = _currentWeather

    private val _forecast = MutableStateFlow<Resource<ForecastResponse>>(Resource.Loading())
    val forecast: StateFlow<Resource<ForecastResponse>> = _forecast

    fun fetchCurrentWeather(city: String) {
        viewModelScope.launch {
            repository.getCurrentWeather(city)
                .onStart {
                    _currentWeather.value = Resource.Loading()
                }
                .catch { e ->
                    _currentWeather.value = Resource.Error("Failed to load current weather: ${e.localizedMessage}")
                }
                .collect { result ->
                    _currentWeather.value = result
                }
        }
    }

    fun fetchForecast(city: String) {
        viewModelScope.launch {
            repository.getForecast(city)
                .onStart {
                    _forecast.value = Resource.Loading()
                }
                .catch { e ->
                    _forecast.value = Resource.Error("Failed to load forecast: ${e.localizedMessage}")
                }
                .collect { result ->
                    _forecast.value = result
                }
        }
    }
}
