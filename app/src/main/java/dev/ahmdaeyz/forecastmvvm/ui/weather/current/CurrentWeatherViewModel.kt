package dev.ahmdaeyz.forecastmvvm.ui.weather.current

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.ahmdaeyz.forecastmvvm.data.db.entity.WeatherEntry
import dev.ahmdaeyz.forecastmvvm.data.repository.ForecastRepository
import dev.ahmdaeyz.forecastmvvm.internal.lazyDeferred
import dev.ahmdaeyz.forecastmvvm.ui.base.ScopedViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class CurrentWeatherViewModel(
    private val forecastRepository: ForecastRepository
) : ScopedViewModel() {
    override val viewModelJob = Job()

    val weatherDef by lazyDeferred<LiveData<WeatherEntry>>{
        forecastRepository.getCurrentWeather()
    }
    val weatherLocation by lazyDeferred {
        forecastRepository.getWeatherLocation()
    }

}