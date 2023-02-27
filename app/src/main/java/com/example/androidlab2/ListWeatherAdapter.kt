package com.example.androidlab2

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import com.example.androidlab2.databinding.ItemCityBinding

class ListWeatherAdapter(
    private val listRep: List<City>?,
    private val onItemClick: (City) -> Unit
) : ListAdapter<City, ViewWeatherHolder>(
    object : ItemCallback<City>() {
    override fun areItemsTheSame(oldItem: City, newItem: City): Boolean =
        oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: City, newItem: City): Boolean =
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


    override fun onBindViewHolder(
        holder: ViewWeatherHolder,
        position: Int
    ) {
        val currentItem = listRep?.get(position)
        holder.onBind(currentItem as City)
    }

    override fun getItemCount(): Int{
        if (!listRep.isNullOrEmpty())
            return listRep.size
        return 0
    }
}
