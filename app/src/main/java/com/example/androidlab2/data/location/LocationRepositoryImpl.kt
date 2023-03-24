package com.example.androidlab2.data.location

import android.annotation.SuppressLint
import com.example.androidlab2.domain.location.Location
import com.example.androidlab2.domain.location.LocationRepository
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.tasks.await

class LocationRepositoryImpl(
    private val fusedLocationProviderClient: FusedLocationProviderClient
) : LocationRepository {

    @SuppressLint("MissingPermission")
    override suspend fun getLocation(): Location? = fusedLocationProviderClient.lastLocation.await().let {
        if (it != null) {
            Location(
                lat = it.latitude,
                lon = it.longitude
            )
        } else {
            null
        }
    }
}
