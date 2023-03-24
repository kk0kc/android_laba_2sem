package com.example.androidlab2.domain.wheather

import androidx.annotation.ColorRes

data class WeatherListInfo(
    val id: Int,
    val name: String,
    val icon: String,
    val temp: String,
    @ColorRes val tempColor: Int,
)
