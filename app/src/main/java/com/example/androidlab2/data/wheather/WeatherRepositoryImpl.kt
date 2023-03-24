package com.example.androidlab2.data.wheather

import com.example.androidlab2.data.wheather.datasourse.remote.WeatherApi
import com.example.androidlab2.data.wheather.mapper.toWeatherInfo
import com.example.androidlab2.data.wheather.mapper.toWeatherListInfo
import com.example.androidlab2.domain.wheather.WeatherInfo
import com.example.androidlab2.domain.wheather.WeatherListInfo
import com.example.androidlab2.domain.wheather.WeatherRepository

class WeatherRepositoryImpl(
    private val api: WeatherApi
): WeatherRepository {
    override suspend fun getWeatherById(id: Int): WeatherInfo {
        return api.getWeather(id).toWeatherInfo()
    }

    override suspend fun getWeatherByName(name: String): WeatherInfo {
        return api.getWeather(name).toWeatherInfo()
    }

    override suspend fun getNearbyCities(lat: Double?, lon: Double?, cnt: Int): List<WeatherListInfo> {
        return api.getCities(lat,lon, cnt).toWeatherListInfo()
    }

}
