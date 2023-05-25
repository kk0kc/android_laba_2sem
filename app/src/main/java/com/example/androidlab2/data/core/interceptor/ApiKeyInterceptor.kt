package com.example.androidlab2.data.core.interceptor

import com.example.androidlab2.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val newURL = original.url.newBuilder()
            .addQueryParameter("appid", BuildConfig.API_KEY)
            .build()
        return chain.proceed(
            original.newBuilder()
                .url(newURL)
                .build()
        )
    }
}
