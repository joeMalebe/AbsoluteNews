package com.example.absolutesport.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.absolutesport.R
import com.example.absolutesport.network.Article
import kotlinx.android.synthetic.main.activity_article_view.*


class ArticleViewArticleActivity : AppCompatActivity() {

    companion object {
        private const val EXTRA_ARTICLE = "EXTRA_ARTICLE"

        fun getStartIntent(context: Context, article: Article): Intent {
            val intent = Intent(context, ArticleViewArticleActivity::class.java)
            intent.putExtra(EXTRA_ARTICLE, article)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article_view)

        val extras = intent.extras
        if (extras != null && extras.get(EXTRA_ARTICLE) != null) {

            val article = extras.get(EXTRA_ARTICLE) as Article
            displayScreen(article)
        }
    }

    private fun displayScreen(article: Article) {
        text_content.setText(article.content)
    }
}