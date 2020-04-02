package dev.ahmdaeyz.forecastmvvm.data.provider

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import dev.ahmdaeyz.forecastmvvm.data.db.entity.WeatherLocation
import dev.ahmdaeyz.forecastmvvm.internal.LocationPermissionNotGrantedException
import dev.ahmdaeyz.forecastmvvm.internal.asDeferred
import kotlinx.coroutines.Deferred
import java.util.jar.Manifest

const val USE_DEVICE_LOCATION = "USE_DEVICE_LOCATION"
const val USE_CUSTOM_LOCATION = "CUSTOM_LOCATION"
class LocationProviderImpl(
    private val fusedLocationProviderClient: FusedLocationProviderClient,
    context: Context
) : PreferenceProvider(context),LocationProvider {
    private val appContext: Context = context.applicationContext


    override suspend fun getPreferredLocationString(): String {
        if(isUsingDeviceLocation()){
            try {
                val deviceLocation = getLastDeviceLocation().await()
                    ?: return "${getCustomLocationCityName()}"
//                return
            }catch (e: LocationPermissionNotGrantedException){

            }
        }
        return "Cairo"
    }

    override suspend fun hasLocationChanged(lastWeatherLocation: WeatherLocation): Boolean {
        val deviceLocationChanged = try {
             hasDeviceLocationChanged(lastWeatherLocation)
        }catch (e: LocationPermissionNotGrantedException){
            return false
        }
        return deviceLocationChanged || hasCustomLocationChanged(lastWeatherLocation)
    }


    private fun hasCustomLocationChanged(lastWeatherLocation: WeatherLocation): Boolean {
        val customLocationCityName = getCustomLocationCityName()
        return customLocationCityName == lastWeatherLocation.cityName
    }

    private suspend fun hasDeviceLocationChanged(lastWeatherLocation: WeatherLocation): Boolean {
        if(!isUsingDeviceLocation())
            return false
        val deviceLocation = getLastDeviceLocation().await()?: return false

        val comparsionThreshold = 0.03

        return Math.abs(deviceLocation.latitude - lastWeatherLocation.coordinates.lat) > comparsionThreshold &&
                Math.abs(deviceLocation.longitude - lastWeatherLocation.coordinates.lon) > comparsionThreshold

    }

    @SuppressLint("MissingPermission")
    private fun getLastDeviceLocation(): Deferred<Location?> {
        if (hasLocationPermission()){
            return fusedLocationProviderClient.lastLocation.asDeferred()
        }else{
            throw LocationPermissionNotGrantedException()
        }

    }

    private fun isUsingDeviceLocation(): Boolean = preferences.getBoolean(USE_DEVICE_LOCATION,true)
    private fun getCustomLocationCityName(): String? = preferences.getString(USE_CUSTOM_LOCATION,null)

    private fun hasLocationPermission(): Boolean{
        return ContextCompat.checkSelfPermission(appContext,
            android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }
}