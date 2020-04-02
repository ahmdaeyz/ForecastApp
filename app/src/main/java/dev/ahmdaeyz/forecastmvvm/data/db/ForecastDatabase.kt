package dev.ahmdaeyz.forecastmvvm.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import dev.ahmdaeyz.forecastmvvm.data.db.entity.WeatherEntry
import dev.ahmdaeyz.forecastmvvm.data.db.entity.WeatherLocation

@Database(
    entities = [WeatherEntry::class, WeatherLocation::class],
    version = 1
)
abstract class ForecastDatabase : RoomDatabase(){
    abstract fun currentWeatherDao(): CurrentWeatherDAO
    abstract fun weatherLocationDao(): WeatherLocationDAO
    companion object{
        @Volatile private var instance: ForecastDatabase? =null
        private val LOCK = Any()
        operator fun invoke(context: Context) = instance?: synchronized(LOCK){
            buildDatabase(context).also { instance = it }
        }
        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                ForecastDatabase::class.java,
                "forecast.db"
            ).build()
    }
}