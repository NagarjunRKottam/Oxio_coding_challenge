package com.oxio.weather.data

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    @SerializedName("weather") val weather: List<Weather>,
    @SerializedName("main") val main: Main,
    @SerializedName("wind") val wind: Wind
)

data class ForecastResponse(
    @SerializedName("list") val list: List<ForecastItem>
)

data class Weather(
    @SerializedName("main") val main: String,
    @SerializedName("description") val description: String,
    @SerializedName("icon") val icon: String
)

data class Main(
    @SerializedName("temp") val temp: Double,
    @SerializedName("feels_like") val feelsLike: Double,
    @SerializedName("humidity") val humidity: Int
)

data class Wind(
    @SerializedName("speed") val speed: Double
)

data class ForecastItem(
    @SerializedName("dt_txt") val dateText: String,
    @SerializedName("main") val main: Main,
    @SerializedName("weather") val weather: List<Weather>
)
