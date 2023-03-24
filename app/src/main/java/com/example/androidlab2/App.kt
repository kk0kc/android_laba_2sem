package com.example.androidlab2

import android.app.Application
import android.content.Context
import timber.log.Timber
import timber.log.Timber.DebugTree

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext

        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }
    }
    companion object {
        lateinit var appContext: Context
            private set
    }
}
