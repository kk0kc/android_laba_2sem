package com.example.androidlab2.di

import com.example.androidlab2.data.wheather.WeatherRepositoryImpl
import com.example.androidlab2.data.wheather.datasourse.remote.WeatherApi
import com.example.androidlab2.domain.wheather.GetWeatherByNameUseCase
import com.example.androidlab2.domain.wheather.GetWeatherListUseCase
import com.example.androidlab2.domain.wheather.WeatherRepository
import dagger.Module
import dagger.Provides

@Module
class WeatherModule {

    @Provides
    fun provideWeatherRepository(
        weatherApi: WeatherApi
    ) : WeatherRepository = WeatherRepositoryImpl(weatherApi)

    @Provides
    fun provideWeatherByNameUseCase(
            weatherRepository: WeatherRepository
    ) : GetWeatherByNameUseCase = GetWeatherByNameUseCase(weatherRepository)

    @Provides
    fun provideWeatherListUseCase(
        weatherRepository: WeatherRepository
    ) : GetWeatherListUseCase = GetWeatherListUseCase(weatherRepository)
}
