package dev.ahmdaeyz.forecastmvvm.data.db.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
const val CURRENT_WEATHER_ID = 0
@Entity(tableName = "current_weather")
data class WeatherEntry(
    @PrimaryKey(autoGenerate = false)
    val id: Int = CURRENT_WEATHER_ID,
    @Embedded(prefix = "weather_")
    val weather: Weather,
    @Embedded(prefix = "wind_")
    val wind: Wind,
    @Embedded(prefix = "temperature_")
    val temperature: Temperature,
    val visibility: Int
    )