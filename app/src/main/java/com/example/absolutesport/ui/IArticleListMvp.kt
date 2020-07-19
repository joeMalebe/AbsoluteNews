package com.example.absolutesport.ui

import com.example.absolutesport.Topic
import com.example.absolutesport.network.Article

interface IArticleListMvp {
    interface View{
        fun displayScreen()
        fun showLoading()
        fun displayArticles(articles: List<Article>)
        fun dismissLoading()
    }
    interface Presenter{
        fun getTopHeadlines(topic: Topic)
        fun attach(view:View)
        fun detach()
    }
}