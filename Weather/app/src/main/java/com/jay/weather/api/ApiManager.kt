package com.jay.weather.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object ApiManager {

    private const val BASE_URL = "https://api.openweathermap.org"
    private const val APP_ID = "f77a19fea5fb15784e28e4646759d021"

    fun getApiService(): ApiService =
        getRetrofit().create(ApiService::class.java)

    private fun getRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(getOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

    private fun getOkHttpClient(): OkHttpClient =
        OkHttpClient().newBuilder()
            .addInterceptor { chain ->
                val request = chain.request()
                val url = request.url().newBuilder()
                    .addQueryParameter("APPID", APP_ID)
                    .build()
                chain.proceed(request.newBuilder().url(url).build())
            }.build()

}