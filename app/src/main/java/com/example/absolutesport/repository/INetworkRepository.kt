package com.example.absolutesport.repository

import com.example.absolutesport.network.Article

interface INetworkRepository {

    fun getTopHeadlines(country: String, category: String): List<Article>
}