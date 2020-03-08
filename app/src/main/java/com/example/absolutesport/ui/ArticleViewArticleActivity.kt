package com.example.absolutesport.ui

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.Log
import android.view.View
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
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val extras = intent.extras
        if (extras != null && extras.get(EXTRA_ARTICLE) != null) {

            val article = extras.get(EXTRA_ARTICLE) as Article
            displayScreen(article)
        }
    }

    private fun displayScreen(article: Article) {
        text_article_title.setText(article.title)

        val content =
            if (article.content != null) {
                addReadMoreString(article.content)
            } else {
                getString(R.string.read_more)
            }

        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(article.url))
                startActivity(intent)
            }
        }

        val spannableString = SpannableString(content)
        spannableString.setSpan(
            clickableSpan,
            content.length - getString(R.string.read_more).length,
            content.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        text_content.setText(spannableString)
        text_content.movementMethod = LinkMovementMethod.getInstance()
        try {
            image_article_bilboard.setImageBitmap(BitmapFactory.decodeStream(openFileInput(article.hashCode().toString())))

        } catch (exception: FileNotFoundException) {
            Log.e(ArticleViewArticleActivity::class.simpleName, exception.message)
            image_article_bilboard.setImageResource(
                R.drawable.ic_broken_image_black_24dp

            )
        }
    }

    override fun onBackPressed() {
        finish()
    }

    private fun addReadMoreString(content: String): String {
        return if (content.contains(Regex(REGEX_PATTERN))) {
            content.replace(Regex(REGEX_PATTERN), getString(R.string.read_more))
        } else {
            content
        }
    }
}