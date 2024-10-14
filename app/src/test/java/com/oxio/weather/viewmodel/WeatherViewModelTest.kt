package com.oxio.weather.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.oxio.weather.data.WeatherResponse
import com.oxio.weather.data.ForecastItem
import com.oxio.weather.data.ForecastResponse
import com.oxio.weather.data.Main
import com.oxio.weather.data.Weather
import com.oxio.weather.data.Wind
import com.oxio.weather.services.Resource
import com.oxio.weather.services.WeatherRepository
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class WeatherViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: WeatherViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Mock
    private lateinit var weatherRepository: WeatherRepository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        viewModel = WeatherViewModel(weatherRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `fetchCurrentWeather emits loading and success`() = runTest {
        val loadingState = Resource.Loading<WeatherResponse>()
        val successState = Resource.Success(weatherData)

        whenever(weatherRepository.getCurrentWeather("TestCity")).thenReturn(
            flow {
                emit(loadingState)
                emit(successState)
            }
        )

        val emittedStates = mutableListOf<Resource<WeatherResponse>>()
        val job = launch {
            viewModel.currentWeather.collect { emittedStates.add(it) }
        }

        viewModel.fetchCurrentWeather("TestCity")
        advanceUntilIdle()

        assertTrue(emittedStates[0] is Resource.Loading)
        assertEquals(successState, emittedStates[1])

        job.cancel()
    }

    @Test
    fun `fetchCurrentWeather emits loading and error`() = runTest {
        val loadingState = Resource.Loading<WeatherResponse>()
        val errorState = Resource.Error<WeatherResponse>("Network error")

        whenever(weatherRepository.getCurrentWeather("TestCity")).thenReturn(
            flow {
                emit(loadingState)
                emit(errorState)
            }
        )

        val emittedStates = mutableListOf<Resource<WeatherResponse>>()
        val job = launch {
            viewModel.currentWeather.collect { emittedStates.add(it) }
        }

        viewModel.fetchCurrentWeather("TestCity")
        advanceUntilIdle()

        assertTrue(emittedStates[0] is Resource.Loading)
        assertTrue(emittedStates[1] is Resource.Error && emittedStates[1].message == "Network error")

        job.cancel()
    }

    @Test
    fun `fetchForecast emits loading and success`() = runTest {
        val loadingState = Resource.Loading<ForecastResponse>()
        val successState = Resource.Success(forecastData)

        whenever(weatherRepository.getForecast("TestCity")).thenReturn(
            flow {
                emit(loadingState)
                emit(successState)
            }
        )

        val emittedStates = mutableListOf<Resource<ForecastResponse>>()
        val job = launch {
            viewModel.forecast.collect { emittedStates.add(it) }
        }

        viewModel.fetchForecast("TestCity")
        advanceUntilIdle()

        assertTrue(emittedStates[0] is Resource.Loading)
        assertEquals(successState, emittedStates[1])

        job.cancel()
    }

    @Test
    fun `fetchForecast emits loading and error`() = runTest {
        val loadingState = Resource.Loading<ForecastResponse>()
        val errorState = Resource.Error<ForecastResponse>("API failure")

        whenever(weatherRepository.getForecast("TestCity")).thenReturn(
            flow {
                emit(loadingState)
                emit(errorState)
            }
        )

        val emittedStates = mutableListOf<Resource<ForecastResponse>>()
        val job = launch {
            viewModel.forecast.collect { emittedStates.add(it) }
        }

        viewModel.fetchForecast("TestCity")
        advanceUntilIdle()

        assertTrue(emittedStates[0] is Resource.Loading)
        assertTrue(emittedStates[1] is Resource.Error && emittedStates[1].message == "API failure")

        job.cancel()
    }

    // Mock Data
    private val weatherData = WeatherResponse(
        weather = listOf(
            Weather(
                main = "Clouds",
                description = "Partly cloudy",
                icon = "03d"
            )
        ),
        main = Main(
            temp = 22.5,
            feelsLike = 21.0,
            humidity = 65
        ),
        wind = Wind(
            speed = 5.2
        )
    )

    private val forecastData = ForecastResponse(
        list = listOf(
            ForecastItem(
                dateText = "2023-10-14 12:00:00",
                main = Main(
                    temp = 20.0,
                    feelsLike = 19.0,
                    humidity = 70
                ),
                weather = listOf(
                    Weather(
                        main = "Rain",
                        description = "light rain",
                        icon = "10d"
                    )
                )
            ),
            ForecastItem(
                dateText = "2023-10-15 12:00:00",
                main = Main(
                    temp = 18.5,
                    feelsLike = 17.5,
                    humidity = 75
                ),
                weather = listOf(
                    Weather(
                        main = "Clear",
                        description = "clear sky",
                        icon = "01d"
                    )
                )
            )
        )
    )
}
