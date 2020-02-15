package com.example.absolutesport.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

class NetworkClient {
    val baseUrl = "http://www.mocky.io/v2/"
    val instance by lazy {
        Retrofit.Builder().addConverterFactory(JacksonConverterFactory.create()).baseUrl(baseUrl)
            .build()
    }

}