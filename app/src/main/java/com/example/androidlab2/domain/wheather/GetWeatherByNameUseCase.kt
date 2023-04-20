package com.example.androidlab2.domain.wheather

import com.example.androidlab2.domain.wheather.model.WeatherInfo

class GetWeatherByNameUseCase(
    private val weatherRepository: WeatherRepository
) {
    suspend operator fun invoke(
        name: String
    ): WeatherInfo = weatherRepository.getWeatherByName(name)
}
