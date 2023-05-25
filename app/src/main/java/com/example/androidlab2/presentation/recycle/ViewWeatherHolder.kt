package com.example.androidlab2.presentation.recycle

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.androidlab2.databinding.ItemCityBinding
import com.example.androidlab2.domain.wheather.model.WeatherListInfo

class ViewWeatherHolder(
    private val binding: ItemCityBinding,
    private val onItemClick: (WeatherListInfo) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    @SuppressLint("ResourceAsColor")
    fun onBind(city: WeatherListInfo) {
        with(binding) {
            name.text = city.name
            temp.text = city.temp
            temp.setTextColor(binding.root.context.getColor(city.tempColor))
            root.setOnClickListener{
                onItemClick(city)
            }
            icon.load("https://openweathermap.org/img/w/${city.icon}.png")
        }
    }
}
