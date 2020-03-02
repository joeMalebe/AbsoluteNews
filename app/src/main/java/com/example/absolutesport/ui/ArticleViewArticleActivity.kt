package com.example.absolutesport.ui

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.absolutesport.R
import com.example.absolutesport.network.Article
import kotlinx.android.synthetic.main.activity_article_view.*
import java.io.FileNotFoundException


class ArticleViewArticleActivity : AppCompatActivity() {


    companion object {
        private const val REGEX_PATTERN = "\\[\\+\\d+\\s\\w+]"
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
        text_article_title.setText(article.title)

        text_content.setText(
            if (article.content != null) {
                addReadMoreString(article.content)
            } else {
                getString(R.string.read_more)
            }
        )
        try {
            image_article_bilboard.setImageBitmap(BitmapFactory.decodeStream(openFileInput(article.hashCode().toString())))

        } catch (exception: FileNotFoundException) {
            Log.e(ArticleViewArticleActivity::class.simpleName, exception.message)
            image_article_bilboard.setImageResource(
                R.drawable.ic_broken_image_black_24dp

            )
        }
    }

    private fun addReadMoreString(content: String): String {
        return if (content.contains(Regex(REGEX_PATTERN))) {
            content.replace(Regex(REGEX_PATTERN), getString(R.string.read_more))
        } else {
            content
        }
    }
}