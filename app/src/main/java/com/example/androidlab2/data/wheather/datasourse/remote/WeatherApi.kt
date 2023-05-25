package com.example.androidlab2.data.wheather.datasourse.remote

import com.example.androidlab2.data.wheather.datasourse.remote.response.CitiesResponse
import com.example.androidlab2.data.wheather.datasourse.remote.response.WeatherResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("weather")
    fun getWeather(
        @Query("q") city: String?,
    ): Single<WeatherResponse>

    @GET("weather")
    fun getWeather(
        @Query("id") cityId: Int,
    ): Single<WeatherResponse>

    @GET("find")
    fun getCities(
        @Query("lat") latitude: Double?,
        @Query("lon") longitude: Double?,
        @Query("cnt") count: Int = 10,
    ): Single<CitiesResponse>
}
