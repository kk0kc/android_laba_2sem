package com.example.androidlab2.presentation.fragment.viewmodel

import androidx.lifecycle.*
import com.example.androidlab2.domain.wheather.GetWeatherByNameUseCase
import com.example.androidlab2.domain.wheather.model.WeatherInfo
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.kotlin.subscribeBy

class DetailViewModel @AssistedInject constructor(
    private val getWeatherByNameUseCase: GetWeatherByNameUseCase,
    @Assisted private val cityId: String
) : ViewModel() {

    private val _error = MutableLiveData<Throwable?>(null)
    val error: LiveData<Throwable?>
        get() = _error

    private var weatherDisposable: Disposable? = null


    private val _weather = MutableLiveData<WeatherInfo?>(null)
    val weather: LiveData<WeatherInfo?>
        get() = _weather


    fun getWeatherInCity() {
        loadWeather()
    }


    private fun loadWeather() {
//        viewModelScope.launch {
//            try {
//                getWeatherByNameUseCase.invoke(cityId).also { weather ->
//                    _weather.value = weather
//                }
//            } catch (error: Throwable) {
//                _error.value = error
//            }
//        }
        weatherDisposable = getWeatherByNameUseCase(cityId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { weatherInfo ->
                    _weather.value = weatherInfo
                }, onError = {error ->
                    _error.value = error
                }
            )
    }

    @AssistedFactory
    interface DetailViewModelFactory {
        fun build(cityId: String): DetailViewModel
    }

    companion object {
        fun provideFactory(
            assistedFactory: DetailViewModelFactory,
            cityId: String
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>
            ): T = assistedFactory.build(cityId) as T
        }
    }
}
