package com.example.androidlab2.domain.wheather

import com.example.androidlab2.domain.wheather.model.WeatherInfo
import javax.inject.Inject

class GetWeatherByNameUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {
    suspend operator fun invoke(
        name: String?
    ): WeatherInfo = weatherRepository.getWeatherByName(name)
}
