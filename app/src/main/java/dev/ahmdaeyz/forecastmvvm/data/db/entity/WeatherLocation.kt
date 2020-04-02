package dev.ahmdaeyz.forecastmvvm.data.db.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.threeten.bp.Instant
import org.threeten.bp.ZoneId
import org.threeten.bp.ZoneOffset
import org.threeten.bp.ZonedDateTime

const val WEATHER_LOCATION_ID = 0
@Entity(tableName = "weather_location")
data class WeatherLocation(
    @PrimaryKey(autoGenerate = false)
    val id: Int = WEATHER_LOCATION_ID,
    val cityName: String,
    val country: String,
    @Embedded(prefix = "coordinate_")
    val coordinates: Coordinates,
    val date: Long,
    val timeZoneOffset: Int
){
    val zonedDateTime: ZonedDateTime
    get() {
        val instant = Instant.ofEpochSecond(date)
        val zoneOffset = ZoneOffset.ofTotalSeconds(timeZoneOffset)
        val zoneId = ZoneId.ofOffset("",zoneOffset)
        return ZonedDateTime.ofInstant(instant,zoneId)
    }
}