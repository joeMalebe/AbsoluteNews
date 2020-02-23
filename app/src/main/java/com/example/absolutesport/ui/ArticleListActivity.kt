package com.example.absolutesport.ui

import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.absolutesport.R
import com.example.absolutesport.network.Article
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_articles_list.*
import java.io.ByteArrayOutputStream

class ArticleListActivity : AppCompatActivity(), IArticleListMvp.View {

    val compositeDisposable = CompositeDisposable()
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
            val adapter = ArticleListAdapter(articles)
            compositeDisposable.add(adapter.getClickedArticle().subscribe({
                cacheArticleImage(it)

                startActivity(
                    ArticleViewArticleActivity.getStartIntent(
                        recycler_article_list.context,
                        it
                    )
                )
            }, {
                Log.e(ArticleListActivity::class.simpleName, it.message)
            }))

            recycler_article_list.adapter = adapter
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

    private fun cacheArticleImage(article: Article) {
        if (article.image != null) {
            val byteArrayOutputStream = ByteArrayOutputStream()
            article.image!!.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
            val fileOutputStream =
                openFileOutput(article.hashCode().toString(), Context.MODE_PRIVATE)
            fileOutputStream.write(byteArrayOutputStream.toByteArray())
            article.image = null
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }

    override fun dismissLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
