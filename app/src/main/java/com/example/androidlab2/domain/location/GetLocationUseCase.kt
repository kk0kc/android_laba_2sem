package com.example.androidlab2.domain.location

import com.example.androidlab2.domain.location.model.Location
import javax.inject.Inject

class GetLocationUseCase @Inject constructor(
    private val locationRepository: LocationRepository
) {
    suspend operator fun invoke(): Location? {
        return locationRepository.getLocation()
    }
}
