package com.example.absolutesport.network

import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.jackson.JacksonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.logging.Logger

interface ApiService {

    @Headers("X-Api-Key:9419ee88f8c743baa5a8b1ee88d1e200")
    @GET("top-headlines")
    fun getTopHeadlines(@Query("country")country:String): Single<TopHeadlinesResponse>

    companion object {
        private const val BASE_URL = "https://newsapi.org/v2/"

        fun create(): ApiService {
            val retrofit = Retrofit.Builder().addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(JacksonConverterFactory.create()).baseUrl(BASE_URL).build()
            Logger.getAnonymousLogger().info("service initialised")
            return retrofit.create(ApiService::class.java)
        }
    }
}
