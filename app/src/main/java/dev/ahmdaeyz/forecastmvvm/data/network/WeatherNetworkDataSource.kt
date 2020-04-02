package dev.ahmdaeyz.forecastmvvm.data.network

import androidx.lifecycle.LiveData
import dev.ahmdaeyz.forecastmvvm.internal.enums.TempertureUnit
import dev.ahmdaeyz.forecastmvvm.data.network.response.CurrentWeather

interface WeatherNetworkDataSource {
    val downloadedCurrentWeather: LiveData<CurrentWeather>
    suspend fun fetchCurrentWeather(
        location: String,
        unit: TempertureUnit,
        langCode: String
    )
}