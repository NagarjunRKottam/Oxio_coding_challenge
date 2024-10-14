package com.oxio.weather.services

import retrofit2.Response
import com.oxio.weather.data.WeatherResponse
import com.oxio.weather.data.ForecastResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("weather")
    suspend fun getCurrentWeather(
        @Query("q") city: String,
        @Query("appid") apiKey:  String = "a4abe5382e6ece8961924f255507c85c"
    ): Response<WeatherResponse> // Correct Retrofit Response type

    @GET("forecast")
    suspend fun getForecast(
        @Query("q") city: String,
        @Query("appid") apiKey:  String = "a4abe5382e6ece8961924f255507c85c"
    ): Response<ForecastResponse> // Correct Retrofit Response type
}
