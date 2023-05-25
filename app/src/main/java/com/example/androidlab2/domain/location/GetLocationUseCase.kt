package com.example.androidlab2.domain.location

import com.example.androidlab2.domain.location.model.Location
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GetLocationUseCase @Inject constructor(
    private val locationRepository: LocationRepository
) {
    operator fun invoke(): Single<Location> {
        return locationRepository.getLocation()
    }
}
