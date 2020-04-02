package dev.ahmdaeyz.forecastmvvm.data.db.entity


import com.google.gson.annotations.SerializedName

data class Weather(
    val description: String,
    val icon: String,
    @SerializedName("main")
    val condition: String
)