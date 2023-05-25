package com.example.androidlab2.domain.location

import com.example.androidlab2.domain.location.model.Location
import io.reactivex.rxjava3.core.Single

interface LocationRepository{
    fun getLocation(): Single<Location>
}
