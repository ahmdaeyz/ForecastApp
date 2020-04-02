package dev.ahmdaeyz.forecastmvvm.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dev.ahmdaeyz.forecastmvvm.internal.enums.TempertureUnit
import dev.ahmdaeyz.forecastmvvm.data.network.response.CurrentWeather
import dev.ahmdaeyz.forecastmvvm.internal.NoConnectivityException

class WeatherNetworkDataSourceImpl(
    private val apiService: OpenWeatherMapApiService
) : WeatherNetworkDataSource {
    var _downloadedCurrentWeather = MutableLiveData<CurrentWeather>()
    override val downloadedCurrentWeather: LiveData<CurrentWeather>
        get() = _downloadedCurrentWeather
    override suspend fun fetchCurrentWeather(
        location: String,
        unit: TempertureUnit,
        langCode: String
    ) {
        try {
            val currentWeather = apiService
                .getCurrentWeatherAsync(location,unit.value,langCode)
                .await()
            _downloadedCurrentWeather.value = currentWeather
        }catch (e: Exception){
            Log.e("Connectivity","No Internet Connection.")
        }
    }

}