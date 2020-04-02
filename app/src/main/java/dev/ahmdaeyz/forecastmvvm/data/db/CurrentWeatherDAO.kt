package dev.ahmdaeyz.forecastmvvm.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.ahmdaeyz.forecastmvvm.data.db.entity.CURRENT_WEATHER_ID
import dev.ahmdaeyz.forecastmvvm.data.db.entity.WeatherEntry

@Dao
interface CurrentWeatherDAO {
     @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun upsert(weatherEntry: WeatherEntry): Unit
    @Query("SELECT * FROM current_weather WHERE id=$CURRENT_WEATHER_ID")
    fun getCurrentWeather(): LiveData<WeatherEntry>
}