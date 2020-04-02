package dev.ahmdaeyz.forecastmvvm.data.repository

import androidx.lifecycle.LiveData
import dev.ahmdaeyz.forecastmvvm.data.db.CurrentWeatherDAO
import dev.ahmdaeyz.forecastmvvm.data.db.WeatherLocationDAO
import dev.ahmdaeyz.forecastmvvm.data.db.entity.WeatherEntry
import dev.ahmdaeyz.forecastmvvm.data.db.entity.WeatherLocation
import dev.ahmdaeyz.forecastmvvm.internal.enums.TempertureUnit
import dev.ahmdaeyz.forecastmvvm.data.network.WeatherNetworkDataSource
import dev.ahmdaeyz.forecastmvvm.data.network.response.CurrentWeather
import dev.ahmdaeyz.forecastmvvm.data.provider.LocationProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.ZonedDateTime
import java.util.*


class ForecastRepositoryImpl(
    private val currentWeatherDAO: CurrentWeatherDAO,
    private val weatherLocationDAO: WeatherLocationDAO,
    private val locationProvider: LocationProvider,
    private val weatherNetworkDataSource: WeatherNetworkDataSource
) : ForecastRepository {
    init {
        GlobalScope.launch(Dispatchers.Main) {
            initWeatherData()
        }
        weatherNetworkDataSource.downloadedCurrentWeather.observeForever {newCurrentWeather->
            persistFetchedCurrentWeather(newCurrentWeather)
        }
    }
    override suspend fun getCurrentWeather(): LiveData<WeatherEntry> {
        return withContext(Dispatchers.IO){
                return@withContext currentWeatherDAO.getCurrentWeather()
        }
    }

    override suspend fun getWeatherLocation(): LiveData<WeatherLocation> {
        return withContext(Dispatchers.IO){
            return@withContext weatherLocationDAO.getLocation()
        }
    }

    private fun persistFetchedCurrentWeather(fetchedWeather: CurrentWeather){
        GlobalScope.launch(Dispatchers.IO) {
            val toBeStoredWeather = WeatherEntry(
                weather = fetchedWeather.weather[0],
                wind = fetchedWeather.wind,
                temperature = fetchedWeather.temperature,
                visibility = fetchedWeather.visibility
            )
            val toBeStoredLocation = WeatherLocation(
                cityName = fetchedWeather.cityName,
                country = fetchedWeather.locality.country,
                coordinates = fetchedWeather.coordinates,
                date = fetchedWeather.date,
                timeZoneOffset = fetchedWeather.timeZoneOffset
            )
            currentWeatherDAO.upsert(toBeStoredWeather)
            weatherLocationDAO.upsert(toBeStoredLocation)
        }
    }
    private suspend fun initWeatherData(){
        val lastWeatherLocation = weatherLocationDAO.getLocation().value
        if (lastWeatherLocation != null) {
            if (isFetchCurrentNeeded(lastWeatherLocation.zonedDateTime)){
                fetchCurrentWeather()
            }
        }
        if (lastWeatherLocation == null
            || locationProvider.hasLocationChanged(lastWeatherLocation)){
            fetchCurrentWeather()
        }
    }
    private suspend fun fetchCurrentWeather(){
        weatherNetworkDataSource.fetchCurrentWeather(
            locationProvider.getPreferredLocationString(),
            TempertureUnit.METRIC,
            Locale.getDefault().language
        )
    }
    private fun isFetchCurrentNeeded(lastFetchTime: ZonedDateTime): Boolean{
        val thirtyMinutesAgo = ZonedDateTime.now().minusMinutes(30)
        return lastFetchTime.isBefore(thirtyMinutesAgo)
    }
}