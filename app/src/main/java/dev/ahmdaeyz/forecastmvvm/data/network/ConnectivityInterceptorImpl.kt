package dev.ahmdaeyz.forecastmvvm.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import dev.ahmdaeyz.forecastmvvm.internal.NoConnectivityException
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class ConnectivityInterceptorImpl(context: Context) : ConnectivityInterceptor {
    private val appContext = context.applicationContext
    override fun intercept(chain: Interceptor.Chain): Response {
        if(!isOnline()){
            throw NoConnectivityException()
        }
        return  chain.proceed(chain.request())
    }
    private fun isOnline(): Boolean{
        val connectivityManager = appContext.getSystemService(Context.CONNECTIVITY_SERVICE)
        as ConnectivityManager
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities!=null){
                if(capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)||
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)||
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                        ){
                    return true
                }
            }
        return false
    }
}