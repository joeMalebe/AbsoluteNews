package com.example.absolutesport.repository

import com.example.absolutesport.Topic
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

    fun getTopHeadlines(topic: Topic): Single<List<Article>> {
        val category = if (topic != Topic.SA_NEWS) {
            topic.category
        } else {
            ""
        }
        return apiService.getTopHeadlines(
            "za",
            category
        ).subscribeOn(Schedulers.io()).map { response ->
            Logger.getAnonymousLogger().info("map response with status ${response.status}")
            response.articles
        }
    }
}