package com.example.androidlab2.domain.wheather

data class WeatherInfo(
    val id: Int,
    val name: String,
    val temperature: Double,
    val humidity: Int,
    val tempMax: Double,
    val tempMin: Double,
    val windSpeed: Double,
    val pressure: Int,
    val feelsLike: Double,
    val gust: Double,
    val icon: String
)
