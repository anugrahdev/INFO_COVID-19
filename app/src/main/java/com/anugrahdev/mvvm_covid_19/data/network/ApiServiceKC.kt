package com.anugrahdev.mvvm_covid_19.data.network

import com.anugrahdev.mvvm_covid_19.data.db.entities.globaldata.GlobalDataResponse
import com.anugrahdev.mvvm_covid_19.data.db.entities.indodata.IndoDataResponse
import com.anugrahdev.mvvm_covid_19.data.db.entities.indodatasum.IndoDataSumResponse
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.util.concurrent.TimeUnit

interface ApiServiceKC {


    @GET("/")
    suspend fun getCovidDataGlobal():Response<GlobalDataResponse>

    @GET("indonesia/provinsi")
    suspend fun getCovidIndoData():Response<IndoDataResponse>

    @GET("indonesia/")
    suspend fun getCovidIndoSum():Response<IndoDataSumResponse>

    companion object{
        operator fun invoke(connectivityInterceptor: NetworkInterceptor): ApiServiceKC{
            val okHttpClient = OkHttpClient.Builder()
                .callTimeout(5,TimeUnit.MINUTES)
                .writeTimeout(5, TimeUnit.MINUTES)
                .readTimeout(5,TimeUnit.MINUTES)
                .addInterceptor(connectivityInterceptor)
                .build()
            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://api.kawalcorona.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiServiceKC::class.java)
        }
    }
}