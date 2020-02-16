package com.example.absolutesport.repository

import com.example.absolutesport.network.ApiService
import com.example.absolutesport.network.Article
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import java.util.logging.Logger

class NetworkRepository {

    val apiService by lazy {
        Logger.getAnonymousLogger().info("service initialising")
        ApiService.create()
    }

    fun getTopHeadlines(): Single<List<Article>> {
        return apiService.getTopHeadlines("za").subscribeOn(Schedulers.io()).map { response ->
            Logger.getAnonymousLogger().info("map response with status ${response.status}")
            response.articles
        }
    }
}