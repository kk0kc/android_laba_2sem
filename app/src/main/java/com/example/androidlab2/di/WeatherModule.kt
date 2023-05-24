package com.example.androidlab2.di

import com.example.androidlab2.data.wheather.WeatherRepositoryImpl
import com.example.androidlab2.domain.wheather.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(includes = [BindWeatherModule::class])
@InstallIn(SingletonComponent::class)
class WeatherModule {
//    @Provides
//    fun provideWeatherRepository(
//        weatherApi: WeatherApi
//    ) : WeatherRepository = WeatherRepositoryImpl(weatherApi)
}


@Module
@InstallIn(SingletonComponent::class)
interface BindWeatherModule {
    @Binds
    fun bindWeatherRepository(
        weatherRepositoryImpl: WeatherRepositoryImpl
    ): WeatherRepository
}
