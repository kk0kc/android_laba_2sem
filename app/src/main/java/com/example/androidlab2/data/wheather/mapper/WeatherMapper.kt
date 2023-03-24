package com.example.androidlab2.data.wheather.mapper

import com.example.androidlab2.data.wheather.datasourse.remote.response.CitiesResponse
import com.example.androidlab2.R
import com.example.androidlab2.data.wheather.datasourse.remote.response.WeatherResponse
import com.example.androidlab2.domain.wheather.WeatherInfo
import com.example.androidlab2.domain.wheather.WeatherListInfo

fun WeatherResponse.toWeatherInfo(): WeatherInfo = WeatherInfo(
    id = id,
    name = name,
    humidity = main.humidity,
    temperature = main.temp,
    windSpeed = wind.speed,
    pressure = main.pressure,
    feelsLike = main.feelsLike,
    tempMax = main.tempMax,
    tempMin = main.tempMin,
    icon = weather.first().icon,
    gust = wind.gust
)

fun CitiesResponse.toWeatherListInfo(): List<WeatherListInfo> {
    return list.map {
        val color = it.main.let { main ->
            when {
                main.temp < -20.0 -> R.color.dark_blue
                main.temp in -20.0..0.0 -> R.color.blue
                main.temp == 0.0 -> R.color.green
                main.temp in 0.0..20.0 -> R.color.orange
                main.temp > 20 -> R.color.red
                else -> R.color.black
            }
        }

        WeatherListInfo(
            id = it.id,
            name = it.name,
            icon = it.weather[0].icon,
            tempColor = color,
            temp = it.main.temp.toString()
        )
    }.toMutableList()
}
