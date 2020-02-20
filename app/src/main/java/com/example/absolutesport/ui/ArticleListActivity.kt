package com.example.absolutesport.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.absolutesport.R
import com.example.absolutesport.network.Article
import kotlinx.android.synthetic.main.activity_articles_list.*

class ArticleListActivity : AppCompatActivity(), IArticleListMvp.View {

    val presenter: IArticleListMvp.Presenter = ArticleListPresenter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_articles_list)
        presenter.attach(this)

        displayScreen()
    }

    override fun displayScreen() {
        presenter.getTopHeadlines()
    }

    override fun showLoading() {
        Log.d(ArticleListActivity::class.simpleName, "loading")
    }

    override fun displayArtivles(articles: List<Article>) {
        if (!articles.isEmpty()) {
            recycler_article_list.adapter = ArticleListAdapter(articles)
            recycler_article_list.layoutManager = LinearLayoutManager(this)

            recycler_article_list.addItemDecoration(
                DividerItemDecoration(
                    recycler_article_list.context,
                    LinearLayoutManager.VERTICAL
                )
            )
            Log.d("LandingActivity", "${articles.size}")
        }
    }

    override fun dismissLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
