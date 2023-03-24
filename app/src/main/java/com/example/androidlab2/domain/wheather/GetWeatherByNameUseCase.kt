package com.example.androidlab2.domain.wheather

class GetWeatherByNameUseCase(
    private val weatherRepository: WeatherRepository
) {
    suspend operator fun invoke(
        name: String
    ): WeatherInfo = weatherRepository.getWeatherByName(name)
}
