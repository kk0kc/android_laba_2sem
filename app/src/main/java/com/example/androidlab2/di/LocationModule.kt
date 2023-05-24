package com.example.androidlab2.di

import android.content.Context
import com.example.androidlab2.data.location.LocationRepositoryImpl
import com.example.androidlab2.domain.location.LocationRepository
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module(includes = [BindLocationModule::class])
@InstallIn(ViewModelComponent::class)
class LocationModule {

    @Provides
    fun provideFusedLocationClient(
        applicationContext: Context
    ) : FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(applicationContext)
}

@Module
@InstallIn(ViewModelComponent::class)
interface BindLocationModule{
    @Binds
    fun bindLocationRepository(
        locationRepositoryImpl: LocationRepositoryImpl
    ): LocationRepository

}
