package com.example.absolutesport.ui

import com.example.absolutesport.network.Article

interface ILandingMvp {
    interface View{
        fun displayScreen()
        fun showLoading()
        fun displayArtivles(articles: List<Article>)
        fun dismissLoading()
    }
    interface Presenter{
        fun getTopHeadlines()
        fun attach(view:View)
        fun detach()
    }
}