package com.example.androidlab2.domain.wheather

import com.example.androidlab2.domain.wheather.model.WeatherInfo
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GetWeatherByNameUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {
    operator fun invoke(
        name: String?
    ): Single<WeatherInfo> = weatherRepository.getWeatherByName(name)
}
