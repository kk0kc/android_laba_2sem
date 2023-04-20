package com.example.androidlab2.di

import android.content.Context
import com.example.androidlab2.data.location.LocationRepositoryImpl
import com.example.androidlab2.domain.location.GetLocationUseCase
import com.example.androidlab2.domain.location.LocationRepository
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides

@Module
class LocationModule {

    @Provides
    fun provideFusedLocationClient(
        applicationContext: Context
    ) : FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(applicationContext)

    @Provides
    fun provideLocationRepository(
        fusedLocationProviderClient: FusedLocationProviderClient
    ) : LocationRepository = LocationRepositoryImpl(fusedLocationProviderClient)

    @Provides
    fun provideLocationUseCase(
        locationRepository: LocationRepository
    ) : GetLocationUseCase = GetLocationUseCase(locationRepository)
}
