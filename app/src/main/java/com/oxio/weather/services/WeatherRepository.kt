package com.oxio.weather.services

import com.oxio.weather.data.WeatherResponse
import com.oxio.weather.data.ForecastResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepository @Inject constructor(private val api: WeatherApi) {
    suspend fun getCurrentWeather(city: String): Flow<Resource<WeatherResponse>> = flow {
        emit(Resource.Loading())
        try {
            val response = api.getCurrentWeather(city)
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(Resource.Success(it))
                } ?: emit(Resource.Error("No data"))
            } else {
                emit(Resource.Error("Error: ${response.message()}"))
            }
        } catch (e: Exception) {
            emit(Resource.Error("Network error: ${e.localizedMessage}"))
        }
    }

    suspend fun getForecast(city: String): Flow<Resource<ForecastResponse>> = flow {
        emit(Resource.Loading())
        try {
            val response = api.getForecast(city)
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(Resource.Success(it))
                } ?: emit(Resource.Error("No data"))
            } else {
                emit(Resource.Error("Error: ${response.message()}"))
            }
        } catch (e: Exception) {
            emit(Resource.Error("Network error: ${e.localizedMessage}"))
        }
    }
}



sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)
    class Loading<T>(data: T? = null) : Resource<T>(data)
}
