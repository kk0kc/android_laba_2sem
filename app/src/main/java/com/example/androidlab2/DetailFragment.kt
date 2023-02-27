package com.example.androidlab2

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.lifecycleScope
import coil.load
import com.example.androidlab2.databinding.FragmentDetailBinding
import kotlinx.coroutines.launch
import timber.log.Timber

class DetailFragment : Fragment(R.layout.fragment_detail) {
    private var binding: FragmentDetailBinding? = null
    private val api = DataContainer.weatherApi
    @SuppressLint("SetTextI18n")
    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDetailBinding.bind(view)
        var city = arguments?.getString(CITY_NAME)
        binding?.run {
            if (!city.isNullOrEmpty()) {
                lifecycleScope.launch{
                    name.text = api.getWeather(city).name
                    api.getWeather(city).also {
                        temp.text = it.main.temp.toString() + "°C"
                        Timber.e("${it.id}")
                        icon.load("https://openweathermap.org/img/w/${it.id}.png")
                        humidity.text = "Humidity: " + it.main.humidity.toString()
                        tmin.text = "Min temperature: " + it.main.tempMin.toString()
                        tmax.text = "Max temperature: " + it.main.tempMax.toString()
                        windspeed.text = "Wind speed: " + it.wind.speed.toString()
                        pressure.text = "Pressure: " + it.main.pressure.toString()
                        feelslike.text = "Feels like: " + it.main.feelsLike.toString() + "°C"
                        gust.text = "Gust: " + it.wind.gust.toString()

                    }

                }
            }

        }
    }
    companion object{
        private const val CITY_NAME = "city_name"
        fun newInstance(city: String) = DetailFragment().apply {
            arguments = Bundle().apply {
                putString(CITY_NAME, city)
            }
        }
    }
}
