package com.example.androidlab2.domain.location

import com.example.androidlab2.domain.location.model.Location

interface LocationRepository{
    suspend fun getLocation(): Location?
}
