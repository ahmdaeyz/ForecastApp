package dev.ahmdaeyz.forecastmvvm.data.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dev.ahmdaeyz.forecastmvvm.data.network.response.CurrentWeather
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val APP_ID = "ed2bd920537007b26c359f190d8170d8"
const val BASE_URL = "http://api.openweathermap.org/data/2.5/"
interface OpenWeatherMapApiService {
    @GET("weather")
    fun getCurrentWeatherAsync(
        @Query("q") location: String,
        @Query("units") unit: String,
        @Query("lang") language: String = "en"
    ): Deferred<CurrentWeather>
    companion object{
        operator fun invoke(
            connectivityInterceptor: ConnectivityInterceptor
        ): OpenWeatherMapApiService {
            val requestInterceptor = Interceptor{chain->
                val url = chain.request()
                    .url()
                    .newBuilder()
                    .addQueryParameter("appid",
                        APP_ID
                    )
                    .build()
                val request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()
                return@Interceptor chain.proceed(request)
            }
            val okHttpClient = OkHttpClient
                .Builder()
                .addInterceptor(requestInterceptor)
                .addInterceptor(connectivityInterceptor)
                .build()
            return Retrofit
                .Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(OpenWeatherMapApiService::class.java)
        }
    }
}