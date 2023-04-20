package com.example.androidlab2.presentation.fragment.viewmodel

import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.androidlab2.domain.wheather.GetWeatherByNameUseCase
import com.example.androidlab2.domain.wheather.model.WeatherInfo
import kotlinx.coroutines.launch

class DetailViewModel(
    private val getWeatherByNameUseCase: GetWeatherByNameUseCase,
): ViewModel() {

    private val _error = MutableLiveData<Throwable?>(null)
    val error: LiveData<Throwable?>
        get() = _error


    private val _weather = MutableLiveData<WeatherInfo?>(null)
    val weather: LiveData<WeatherInfo?>
        get() = _weather

    fun getWeatherInCity(cityId: String) {
        loadWeather(cityId)
    }


    private fun loadWeather(cityId: String) {
        viewModelScope.launch {
            try {
                getWeatherByNameUseCase.invoke(cityId).also { weather ->
                    _weather.value = weather
                }
            } catch (error: Throwable) {
                _error.value = error
            }
        }
    }
    companion object {
        fun provideFactory(
            weatherByNameUseCase: GetWeatherByNameUseCase
        ): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                DetailViewModel(weatherByNameUseCase)
            }
        }
    }
}
