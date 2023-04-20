package com.example.androidlab2

import android.app.Application
import androidx.viewbinding.BuildConfig
import com.example.androidlab2.di.*
import timber.log.Timber
import timber.log.Timber.DebugTree

class App : Application() {

    override fun onCreate() {
        super.onCreate()

//        appContext = applicationContext

        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }

        appComponent = DaggerAppComponent.builder()
            .context(applicationContext)
            .build()
    }
    companion object {
        lateinit var appComponent: AppComponent
//        lateinit var appContext: Context
//            private set
    }
}
