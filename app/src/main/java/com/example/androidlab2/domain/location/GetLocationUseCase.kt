package com.example.androidlab2.domain.location

import com.example.androidlab2.domain.location.model.Location

class GetLocationUseCase(
    private val locationRepository: LocationRepository
) {
    suspend operator fun invoke(): Location? {
        return locationRepository.getLocation()
    }
}
