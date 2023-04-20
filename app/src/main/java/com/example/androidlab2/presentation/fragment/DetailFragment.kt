package com.example.androidlab2.presentation.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import coil.load
import com.example.androidlab2.App
import com.example.androidlab2.R
import com.example.androidlab2.databinding.FragmentDetailBinding
import com.example.androidlab2.domain.wheather.GetWeatherByNameUseCase
import com.example.androidlab2.presentation.fragment.viewmodel.DetailViewModel
import javax.inject.Inject

class
DetailFragment : Fragment(R.layout.fragment_detail) {
    private var binding: FragmentDetailBinding? = null

    @Inject
    lateinit var weatherByNameUseCase: GetWeatherByNameUseCase

    private val viewModel: DetailViewModel by viewModels {
        DetailViewModel.provideFactory(weatherByNameUseCase)
}
    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.injectDetail(this)
        super.onCreate(savedInstanceState)
    }
    @SuppressLint("SetTextI18n")
    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDetailBinding.bind(view)
        var city = arguments?.getString(CITY_NAME)
        viewModel.getWeatherInCity(city!!)
        observeViewModel()
    }
    private fun observeViewModel() {
        with(viewModel) {
            weather.observe(viewLifecycleOwner) {
                if (it == null) return@observe
                binding?.run {
                    name.text = it.name
                    temp.text = it.temperature.toString() + "°C"
                    humidity.text = "Humidity: " + it.humidity.toString()
                    tmax.text = "Max temperature: " + it.tempMax.toString()
                    tmin.text = "Min temperature: " + it.tempMin.toString()
                    windspeed.text = "Wind speed: " + it.windSpeed.toString()
                    pressure.text = "Pressure: " + it.pressure.toString()
                    feelslike.text= "Feels like: " + it.feelsLike.toString()
                    gust.text = "Gust: " + it.gust.toString()
                    icon.load("https://openweathermap.org/img/w/${it.icon}.png")
                }
            }

            error.observe(viewLifecycleOwner) {
                if (it == null) return@observe
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
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
