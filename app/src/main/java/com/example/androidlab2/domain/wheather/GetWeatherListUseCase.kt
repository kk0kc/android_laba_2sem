package com.example.androidlab2.domain.wheather

import com.example.androidlab2.domain.wheather.model.WeatherListInfo
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GetWeatherListUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {
    operator fun invoke(
        lat: Double?,
        lon: Double?,
        cnt: Int
    ): Single<List<WeatherListInfo>> = weatherRepository.getNearbyCities(lat, lon, cnt)
}
