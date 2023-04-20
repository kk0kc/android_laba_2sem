package com.example.androidlab2.di

import android.content.Context
import com.example.androidlab2.presentation.fragment.DetailFragment
import com.example.androidlab2.presentation.fragment.MainFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [LocationModule::class, NetworkModule::class, WeatherModule::class])
interface AppComponent {

    fun injectMain(mainFragment: MainFragment)

    fun injectDetail(detailFragment: DetailFragment)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(applicationContext: Context): Builder

        fun build(): AppComponent
    }
}
