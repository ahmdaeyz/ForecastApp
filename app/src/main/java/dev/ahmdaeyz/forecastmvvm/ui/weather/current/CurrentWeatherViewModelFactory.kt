package dev.ahmdaeyz.forecastmvvm.ui.weather.current

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dev.ahmdaeyz.forecastmvvm.data.repository.ForecastRepository

@Suppress("UNCHECKED_CAST")
class CurrentWeatherViewModelFactory(val forecastRepository: ForecastRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CurrentWeatherViewModel(forecastRepository) as T
    }
}