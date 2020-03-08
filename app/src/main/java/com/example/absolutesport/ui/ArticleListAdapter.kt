package com.example.absolutesport.ui

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.absolutesport.R
import com.example.absolutesport.network.Article
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.net.URI


open class ArticleListAdapter(val articles: List<Article>) :
    RecyclerView.Adapter<ArticleListAdapter.ArticleViewHolder>() {
    val publishHyperlinkClickSubject = PublishSubject.create<Article>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_news_article, parent, false)
        return ArticleViewHolder(view)
    }

    override fun getItemCount(): Int {
        return articles.size
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.display(articles[position])
    }

    fun getClickedArticle(): Observable<Article> {
        return publishHyperlinkClickSubject.hide()
    }

    inner class ArticleViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleTextView = view.findViewById<TextView>(R.id.text_article_title)
        //val readMeTextView = view.findViewById<TextView>(R.id.text_read_more)
        val image = view.findViewById<AppCompatImageView>(R.id.image_article_thumbnail)
        val articleInformationLayout =
            view.findViewById<ConstraintLayout>(R.id.layout_article_information)
        val loader = view.findViewById<ProgressBar>(R.id.progress_image_loader)
        lateinit var article: Article
        fun display(article: Article) {
            titleTextView.setText(article.title)
            this.article = article
            articleInformationLayout.setOnClickListener {
                publishHyperlinkClickSubject.onNext(article)
            }


            showLoader()
            if (article.urlToImage != null) {
                val urlToImage = replaceHttpWithHttps(image.context, article.urlToImage)
                loadImageFromUrl(article.urlToImage)
            } else {
                /*article.image =
                    BitmapFactory.decodeResource(image.resources, R.drawable.ic_launcher_background)*/
                dismissLoader()
            }
        }

        private fun showLoader() {
            loader.visibility = View.VISIBLE
            image.visibility = View.GONE
            titleTextView.visibility = View.GONE
        }

        private fun dismissLoader() {
            loader.visibility = View.GONE
            image.visibility = View.VISIBLE
            titleTextView.visibility = View.VISIBLE
        }

        private fun replaceHttpWithHttps(
            context: Context,
            urlToImage: String
        ): String {
            if (urlToImage.contains(context.getString(R.string.http), true)) {
                return urlToImage.replace(
                    context.getString(R.string.http),
                    context.getString(R.string.https)
                )
            } else
                return urlToImage
        }

        private fun loadImageFromUrl(urlToImage: String) {
            getBitmap(
                urlToImage
            ).observeOn(AndroidSchedulers.mainThread()).subscribeOn(
                Schedulers.newThread()
            ).subscribe(DownloadImageFromUrlObserver(image))
        }

        fun getBitmap(url: String?): Observable<Bitmap> {
            return Observable.fromCallable {
                val inputStream = URI(url).toURL().openStream()
                val image: Bitmap = BitmapFactory.decodeStream(inputStream)
                return@fromCallable (image)
            }
        }

        internal inner class DownloadImageFromUrlObserver(
            val image: AppCompatImageView
        ) : Observer<Bitmap> {

            override fun onNext(bitmapFromUrl: Bitmap) {
                image.setImageBitmap(bitmapFromUrl)
                article.image = bitmapFromUrl
                dismissLoader()
            }

            override fun onSubscribe(d: Disposable) {
                Log.d("DownloadImageFromUrl", "subcribed $d")
            }

            override fun onError(error: Throwable) {
                Log.e("DownloadImageFromUrl", error.message)
                dismissLoader()

            }

            override fun onComplete() {
                Log.d("DownloadImageFromUrl", "on complete")
            }
        }

    }
}