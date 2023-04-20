package com.example.androidlab2.domain.wheather

import com.example.androidlab2.domain.wheather.model.WeatherInfo
import com.example.androidlab2.domain.wheather.model.WeatherListInfo

interface WeatherRepository {
    suspend fun getWeatherByName(name: String): WeatherInfo
    suspend fun getWeatherById(id: Int): WeatherInfo
    suspend fun getNearbyCities(lat: Double?, lon: Double?, cnt: Int): List<WeatherListInfo>
}
