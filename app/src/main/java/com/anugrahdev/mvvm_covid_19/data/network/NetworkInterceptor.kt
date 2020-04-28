package com.anugrahdev.mvvm_covid_19.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.anugrahdev.mvvm_covid_19.utils.NoInternetException
import okhttp3.Interceptor
import okhttp3.Response

class NetworkInterceptor(context: Context) : Interceptor{
    private val applicationContext = context.applicationContext
    override fun intercept(chain: Interceptor.Chain): Response {
        if  (isInternetAvailable()!=true){
            throw NoInternetException("Make sure you have an internet connection")
        }
        return chain.proceed(chain.request())
    }

    private fun isInternetAvailable():Boolean{

        var result = false;
        val connectivityManager = applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
            connectivityManager?.let {
                it.getNetworkCapabilities(connectivityManager.activeNetwork)?.apply {
                    result  = when {
                        hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                        hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ->true
                        else->false
                    }
                }
            }

        return result

    }
}