package dev.ahmdaeyz.forecastmvvm.data.repository

import androidx.lifecycle.LiveData
import dev.ahmdaeyz.forecastmvvm.data.db.entity.WeatherEntry
import dev.ahmdaeyz.forecastmvvm.data.db.entity.WeatherLocation

interface ForecastRepository {
    suspend fun getCurrentWeather(): LiveData<WeatherEntry>
    suspend fun getWeatherLocation(): LiveData<WeatherLocation>
}