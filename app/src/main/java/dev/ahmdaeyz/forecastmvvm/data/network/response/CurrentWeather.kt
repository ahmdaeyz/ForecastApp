package dev.ahmdaeyz.forecastmvvm.data.network.response

import com.google.gson.annotations.SerializedName
import dev.ahmdaeyz.forecastmvvm.data.db.entity.*


data class CurrentWeather(
    @SerializedName("id")
    val cityId: Int,
    @SerializedName("coord")
    val coordinates: Coordinates,
    @SerializedName("dt")
    val date: Long,
    @SerializedName("main")
    val temperature: Temperature,
    @SerializedName("name")
    val cityName: String,
    val visibility: Int,
    val weather: List<Weather>,
    val wind: Wind,
    @SerializedName("sys")
    val locality: Locality,
    @SerializedName("timezone")
    val timeZoneOffset: Int
)