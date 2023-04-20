package com.example.androidlab2.presentation.recycle

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import com.example.androidlab2.databinding.ItemCityBinding
import com.example.androidlab2.domain.wheather.model.WeatherListInfo

class ListWeatherAdapter(
    private val onItemClick: (WeatherListInfo) -> Unit
) : ListAdapter<WeatherListInfo, ViewWeatherHolder>(
    object : ItemCallback<WeatherListInfo>() {
    override fun areItemsTheSame(oldItem: WeatherListInfo, newItem: WeatherListInfo): Boolean =
        oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: WeatherListInfo, newItem: WeatherListInfo): Boolean =
        oldItem == newItem
}) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewWeatherHolder = ViewWeatherHolder(
        binding = ItemCityBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ),
        onItemClick = onItemClick)


    override fun onBindViewHolder(holder: ViewWeatherHolder, position: Int) =
        holder.onBind(getItem(position))
}
