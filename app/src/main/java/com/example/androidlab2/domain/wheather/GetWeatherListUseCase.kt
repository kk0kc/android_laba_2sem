package com.example.androidlab2.domain.wheather

class GetWeatherListUseCase(
    private val weatherRepository: WeatherRepository
) {
    suspend operator fun invoke(
        lat: Double?,
        lon: Double?,
        cnt: Int
    ): List<WeatherListInfo> = weatherRepository.getNearbyCities(lat, lon, cnt)
}
