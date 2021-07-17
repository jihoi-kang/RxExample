package com.jay.weather.api

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("/data/2.5/weather")
    fun getWeather(
        @Query("q") query: String,
    ): Single<GetWeatherResponse>

}