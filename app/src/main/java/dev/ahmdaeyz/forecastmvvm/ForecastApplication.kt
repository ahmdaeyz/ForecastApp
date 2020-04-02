package dev.ahmdaeyz.forecastmvvm

import android.app.Application
import androidx.preference.PreferenceManager
import com.jakewharton.threetenabp.AndroidThreeTen
import dev.ahmdaeyz.forecastmvvm.data.db.ForecastDatabase
import dev.ahmdaeyz.forecastmvvm.data.network.*
import dev.ahmdaeyz.forecastmvvm.data.provider.LocationProvider
import dev.ahmdaeyz.forecastmvvm.data.provider.LocationProviderImpl
import dev.ahmdaeyz.forecastmvvm.data.repository.ForecastRepository
import dev.ahmdaeyz.forecastmvvm.data.repository.ForecastRepositoryImpl
import dev.ahmdaeyz.forecastmvvm.ui.weather.current.CurrentWeatherViewModel
import dev.ahmdaeyz.forecastmvvm.ui.weather.current.CurrentWeatherViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.androidModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class ForecastApplication: Application(), KodeinAware {
    override val kodein =  Kodein.lazy {
        import(androidModule(this@ForecastApplication))
        bind() from singleton { ForecastDatabase(instance())}
        bind() from singleton { instance<ForecastDatabase>().currentWeatherDao()}
        bind() from singleton { instance<ForecastDatabase>().weatherLocationDao() }
        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }
        bind() from singleton { OpenWeatherMapApiService(instance())}
        bind<WeatherNetworkDataSource>() with singleton { WeatherNetworkDataSourceImpl(instance()) }
        bind<LocationProvider>() with singleton { LocationProviderImpl() }
        bind<ForecastRepository>() with singleton { ForecastRepositoryImpl(instance(),instance(),instance(),instance()) }
        bind() from provider { CurrentWeatherViewModelFactory(instance()) }

    }

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
        PreferenceManager.setDefaultValues(this,R.xml.preferences,false)
    }
}