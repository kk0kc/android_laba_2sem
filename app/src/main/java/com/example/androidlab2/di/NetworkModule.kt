package com.example.androidlab2.di

import com.example.androidlab2.BuildConfig
import com.example.androidlab2.data.core.interceptor.ApiKeyInterceptor
import com.example.androidlab2.data.core.interceptor.MetricInterceptor
import com.example.androidlab2.data.wheather.datasourse.remote.WeatherApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Named("api_key")
    fun provideApiKeyInterceptor(): Interceptor = ApiKeyInterceptor()

    @Provides
    @Named("metric")
    fun provideMetricInterceptor(): Interceptor = MetricInterceptor()

    @Provides
    fun provideHttpClient(
        @Named("api_key") apiKeyInterceptor: Interceptor,
        @Named("metric") metricInterceptor: Interceptor
    ) : OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(apiKeyInterceptor)
        .addInterceptor(metricInterceptor)
        .connectTimeout(10L, TimeUnit.SECONDS)
        .build()

    @Provides
    fun provideRetrofit(
        httpClient: OkHttpClient,
        gsonFactory: GsonConverterFactory,
        @Named("base_url") baseUrl: String
    ) : Retrofit = Retrofit.Builder()
        .client(httpClient)
        .baseUrl(baseUrl)
        .addConverterFactory(gsonFactory)
        .build()

    @Provides
    fun provideGsonConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    fun provideWeatherApi(
        retrofit: Retrofit
    ): WeatherApi = retrofit.create(WeatherApi::class.java)

    @Provides
    @Named("base_url")
    fun provideBaseUrl(): String = BuildConfig.API_ENDPOINT
}
