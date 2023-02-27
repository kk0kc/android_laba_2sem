package com.example.androidlab2

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.androidlab2.databinding.ItemCityBinding

class ViewWeatherHolder(
    private val binding: ItemCityBinding,
    private val onItemClick: (City) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    @SuppressLint("ResourceAsColor")
    fun onBind(city: City) {
        with(binding) {
            name.text = city.name
            temp.text = city.main.temp.toString()
            when(city.main.temp) {
                in -100.0..-20.0 -> temp.setTextColor(R.color.dark_blue)
                in -20.0..0.0 -> temp.setTextColor(R.color.blue)
                0.0 -> temp.setTextColor(R.color.green)
                in 0.0..20.0 -> temp.setTextColor(R.color.orange)
                in 20.0..100.0  -> temp.setTextColor(R.color.red)
            }
            root.setOnClickListener{
                onItemClick(city)
            }
            city.weather.firstOrNull()?.also {
                icon.load("https://openweathermap.org/img/w/${it.icon}.png")
            }
        }
    }
}
