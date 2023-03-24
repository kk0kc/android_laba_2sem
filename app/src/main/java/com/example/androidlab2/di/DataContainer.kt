package com.example.androidlab2.di

import com.example.androidlab2.BuildConfig
import com.example.androidlab2.data.core.interceptor.ApiKeyInterceptor
import com.example.androidlab2.data.core.interceptor.MetricInterceptor
import com.example.androidlab2.data.location.LocationRepositoryImpl
import com.example.androidlab2.data.wheather.WeatherRepositoryImpl
import com.example.androidlab2.data.wheather.datasourse.remote.WeatherApi
import com.example.androidlab2.domain.location.GetLocationUseCase
import com.example.androidlab2.domain.wheather.GetWeatherByNameUseCase
import com.example.androidlab2.domain.wheather.GetWeatherListUseCase
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import com.example.androidlab2.App.Companion.appContext

object DataContainer {
    private const val BASE_URL = BuildConfig.API_ENDPOINT

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG){
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
    }

    private val httpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(ApiKeyInterceptor())
            .addInterceptor(MetricInterceptor())
            .connectTimeout(10L, TimeUnit.SECONDS)
            .build()
    }

    private val retrofit by lazy {
        Retrofit.Builder()
            .client(httpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val weatherApi = retrofit.create(WeatherApi::class.java)
    private val weatherRepository = WeatherRepositoryImpl(weatherApi)
    private var locationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(appContext)
    private val locationRepository = LocationRepositoryImpl(locationClient)
    val weatherByNameUseCase : GetWeatherByNameUseCase
        get() = GetWeatherByNameUseCase(weatherRepository)
    val weatherListInfo : GetWeatherListUseCase
        get() = GetWeatherListUseCase(weatherRepository)
    val locationUseCase : GetLocationUseCase
        get() = GetLocationUseCase(locationRepository)
}

