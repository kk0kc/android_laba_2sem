package com.example.androidlab2.di
//object DataContainer {
//    private const val BASE_URL = BuildConfig.API_ENDPOINT
//
//    private val loggingInterceptor = HttpLoggingInterceptor().apply {
//        level = if (BuildConfig.DEBUG){
//            HttpLoggingInterceptor.Level.BODY
//        } else {
//            HttpLoggingInterceptor.Level.NONE
//        }
//    }
//
//    private val httpClient by lazy {
//        OkHttpClient.Builder()
//            .addInterceptor(loggingInterceptor)
//            .addInterceptor(ApiKeyInterceptor())
//            .addInterceptor(MetricInterceptor())
//            .connectTimeout(10L, TimeUnit.SECONDS)
//            .build()
//    }
//
//    private val retrofit by lazy {
//        Retrofit.Builder()
//            .client(httpClient)
//            .baseUrl(BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//    }
//
//    val weatherApi = retrofit.create(WeatherApi::class.java)
//    private val weatherRepository = WeatherRepositoryImpl(weatherApi)
//    private var locationClient: FusedLocationProviderClient =
//        LocationServices.getFusedLocationProviderClient(appContext)
//    private val locationRepository = LocationRepositoryImpl(locationClient)
//    val weatherByNameUseCase : GetWeatherByNameUseCase
//        get() = GetWeatherByNameUseCase(weatherRepository)
//    val weatherListInfo : GetWeatherListUseCase
//        get() = GetWeatherListUseCase(weatherRepository)
//    val locationUseCase : GetLocationUseCase
//        get() = GetLocationUseCase(locationRepository)
//}
//
