package dev.ahmdaeyz.forecastmvvm.data.provider

import dev.ahmdaeyz.forecastmvvm.data.db.entity.WeatherLocation

interface LocationProvider {
    suspend fun hasLocationChanged(lastWeatherLocation: WeatherLocation): Boolean
    suspend fun getPreferredLocationString(): String
}