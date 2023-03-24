package com.example.androidlab2.data.wheather.datasourse.remote

import com.example.androidlab2.data.wheather.datasourse.remote.response.CitiesResponse
import com.example.androidlab2.data.wheather.datasourse.remote.response.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("weather")
    suspend fun getWeather(
        @Query("q") city: String,
    ): WeatherResponse

    @GET("weather")
    suspend fun getWeather(
        @Query("id") cityId: Int,
    ): WeatherResponse

    @GET("find")
    suspend fun getCities(
        @Query("lat") latitude: Double?,
        @Query("lon") longitude: Double?,
        @Query("cnt") count: Int = 10,
    ): CitiesResponse
}
