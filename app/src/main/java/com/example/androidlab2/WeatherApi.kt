package com.example.androidlab2

import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("weather")
    suspend fun getWeather(
        @Query("q") city: String,
        @Query("units") units: String = "metric"
    ): WeatherResponse

    @GET("find")
    suspend fun getCities(
        @Query("lat") latitude: Double?,
        @Query("lon") longitude: Double?,
        @Query("cnt") count: Int = 10,
    ): CitiesResponse
}
