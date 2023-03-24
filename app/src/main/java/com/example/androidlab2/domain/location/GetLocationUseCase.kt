package com.example.androidlab2.domain.location

class GetLocationUseCase(
    private val locationRepository: LocationRepository
) {
    suspend operator fun invoke(): Location? {
        return locationRepository.getLocation()
    }
}
