package com.example.androidlab2.data.location

import android.annotation.SuppressLint
import com.example.androidlab2.domain.location.model.Location
import com.example.androidlab2.domain.location.LocationRepository
import com.google.android.gms.location.FusedLocationProviderClient
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    private val fusedLocationProviderClient: FusedLocationProviderClient
) : LocationRepository {

    @SuppressLint("MissingPermission")
    override fun getLocation(): Single<Location> = Single.create { emitter ->
        fusedLocationProviderClient.lastLocation.addOnSuccessListener {
            emitter.onSuccess(
                Location(
                    lat = it.latitude,
                    lon = it.longitude
                )
            )
        }
            .addOnFailureListener {
                emitter.onError(it)
            }
    }
}

