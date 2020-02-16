package com.example.absolutesport.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.absolutesport.R
import com.example.absolutesport.network.Article

class LandingActivity : AppCompatActivity(),ILandingMvp.View {

    val presenter:ILandingMvp.Presenter = LandingPresenter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter.attach(this)
        displayScreen()
    }

    override fun displayScreen() {
        presenter.getTopHeadlines()
    }

    override fun showLoading() {

    }

    override fun displayArtivles(articles: List<Article>) {
        Log.d("LandingActivity","${articles.size}")
    }

    override fun dismissLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
