package com.example.androidlab2.domain.location

interface LocationRepository {
    suspend fun getLocation(): Location?
}
