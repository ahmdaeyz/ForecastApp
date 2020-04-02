package dev.ahmdaeyz.forecastmvvm.data.db.entity


import com.google.gson.annotations.SerializedName

data class Temperature(
    @SerializedName("feels_like")
    val feelsLike: Double,
    val temp: Double,
    @SerializedName("temp_max")
    val tempMax: Double,
    @SerializedName("temp_min")
    val tempMin: Double
)