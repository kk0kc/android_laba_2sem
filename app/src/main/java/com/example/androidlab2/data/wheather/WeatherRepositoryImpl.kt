package com.example.androidlab2.data.wheather

import com.example.androidlab2.data.wheather.datasourse.remote.WeatherApi
import com.example.androidlab2.data.wheather.mapper.toWeatherInfo
import com.example.androidlab2.data.wheather.mapper.toWeatherListInfo
import com.example.androidlab2.domain.wheather.model.WeatherInfo
import com.example.androidlab2.domain.wheather.model.WeatherListInfo
import com.example.androidlab2.domain.wheather.WeatherRepository
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val api: WeatherApi
) : WeatherRepository {
    override fun getWeatherById(id: Int): Single<WeatherInfo> = api.getWeather(id)
        .map {
            it.toWeatherInfo()
        }
        .subscribeOn(Schedulers.io())

    override fun getWeatherByName(name: String?): Single<WeatherInfo> = api.getWeather(name)
        .map {
            it.toWeatherInfo()
        }
        .subscribeOn(Schedulers.io())

    override fun getNearbyCities(
        lat: Double?,
        lon: Double?,
        cnt: Int
    ): Single<List<WeatherListInfo>> = api.getCities(lat, lon, cnt)
        .map {
            it.toWeatherListInfo()
        }
        .subscribeOn(Schedulers.io())
}
