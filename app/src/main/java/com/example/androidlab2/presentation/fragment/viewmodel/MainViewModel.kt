package com.example.androidlab2.presentation.fragment.viewmodel

import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.androidlab2.domain.location.GetLocationUseCase
import com.example.androidlab2.domain.location.model.Location
import com.example.androidlab2.domain.wheather.GetWeatherByNameUseCase
import com.example.androidlab2.domain.wheather.GetWeatherListUseCase
import com.example.androidlab2.domain.wheather.model.WeatherListInfo
import com.example.androidlab2.presentation.recycle.ListWeatherAdapter
import kotlinx.coroutines.launch

class MainViewModel(
    private val getWeatherByNameUseCase: GetWeatherByNameUseCase,
    private val getWeatherListUseCase: GetWeatherListUseCase,
    private val getLocationUseCase: GetLocationUseCase

): ViewModel() {
    private val _adapter = MutableLiveData<ListWeatherAdapter>(null)
    val adapter: LiveData<ListWeatherAdapter>
        get() = _adapter

    private val _location = MutableLiveData<Location>(null)
    val location: LiveData<Location>
        get() = _location

    private val _error = MutableLiveData<Throwable>(null)
    val error: LiveData<Throwable>
        get() = _error

    private val _weatherList = MutableLiveData<List<WeatherListInfo>>(null)
    val weatherList: LiveData<List<WeatherListInfo>>
        get() = _weatherList

    private val _permission = MutableLiveData(false)
    val permission: LiveData<Boolean>
        get() = _permission

    fun onLocationPermsIsGranted(isGranted: Boolean) {
        _permission.value = isGranted
        getList()
    }

    val navigateToDetails: SingleLiveEvent<String> by lazy {
        return@lazy SingleLiveEvent<String>()
    }

    private fun onShouldNavigate(id: String) {
        navigateToDetails.value = id
    }
    fun onNeedLocation() {
        viewModelScope.launch {
            getLocationUseCase().let {
                if (it == null) {
                    if (location.value == Location(LATITUDE, LONGITUDE)) {
                        getList()
                        return@launch
                    }
                    _location.value = Location(LATITUDE, LONGITUDE)
                } else if (permission.value == false) {
                    if (location.value == Location(LATITUDE, LONGITUDE)) {
                        getList()
                        return@launch
                    }
                    _location.value = Location(LATITUDE, LONGITUDE)
                } else {
                    _location.value = it
                }
            }

        }
    }
    fun onLoadClick(query: String) {
        loadWeather(query)
    }

    private fun loadWeather(city: String) {
        viewModelScope.launch {
          try {
              getWeatherByNameUseCase(city).let {
                  it.apply {
                      onShouldNavigate(city)
                  }
              }
          } catch (error: Throwable) {
              _error.value = error
          }
        }
    }

    private fun getListWeather(
        lat: Double?,
        lon: Double?,
        cnt: Int = 10
    ) {
        viewModelScope.launch {
            try {
                getWeatherListUseCase(lat, lon, cnt).let{
                    _weatherList.value = it
                }
            } catch (e: Throwable) {
                _error.value = e
            }
        }
    }

    fun getList() {
        location.value?.run {
            getListWeather(lat, lon)
        }
    }

    companion object {
        private const val LATITUDE = 51.30
        private const val LONGITUDE = 00.07

        fun provideFactory(
                weatherByNameUseCase: GetWeatherByNameUseCase,
                weatherListUseCase: GetWeatherListUseCase,
                locationUseCase: GetLocationUseCase
        ): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                MainViewModel(weatherByNameUseCase, weatherListUseCase, locationUseCase)
            }
        }
    }
}
