package com.example.androidlab2.domain.wheather

import com.example.androidlab2.domain.wheather.model.WeatherInfo
import com.example.androidlab2.domain.wheather.model.WeatherListInfo
import io.reactivex.rxjava3.core.Single

interface WeatherRepository {
    fun getWeatherByName(name: String?): Single<WeatherInfo>
    fun getWeatherById(id: Int): Single<WeatherInfo>
    fun getNearbyCities(lat: Double?, lon: Double?, cnt: Int): Single<List<WeatherListInfo>>
}
