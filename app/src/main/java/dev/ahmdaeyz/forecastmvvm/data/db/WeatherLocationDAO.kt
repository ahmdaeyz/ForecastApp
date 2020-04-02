package dev.ahmdaeyz.forecastmvvm.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.ahmdaeyz.forecastmvvm.data.db.entity.WEATHER_LOCATION_ID
import dev.ahmdaeyz.forecastmvvm.data.db.entity.WeatherLocation
@Dao
interface WeatherLocationDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(weatherLocation: WeatherLocation): Unit
    @Query("SELECT * FROM weather_location WHERE id = $WEATHER_LOCATION_ID")
    fun getLocation(): LiveData<WeatherLocation>
}