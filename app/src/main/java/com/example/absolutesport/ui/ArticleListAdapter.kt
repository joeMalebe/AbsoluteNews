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
import androidx.recyclerview.widget.RecyclerView
import com.example.absolutesport.R
import com.example.absolutesport.network.Article
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.net.URI

open class ArticleListAdapter(val articles: List<Article>) :
    RecyclerView.Adapter<ArticleListAdapter.ArticleViewHolder>() {


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

    class ArticleViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleTextView = view.findViewById<TextView>(R.id.text_article_title)
        val image = view.findViewById<AppCompatImageView>(R.id.image_article_image)
        val loader = view.findViewById<ProgressBar>(R.id.progress_image_loader)

        fun display(article: Article) {
            titleTextView.setText(article.title)
            showLoader()
            if (article.urlToImage != null) {
                val urlToImage = replaceHttpWithHttps(image.context, article.urlToImage)
                loadImageFromUrl(urlToImage)
            } else {
                dismissLoader()
            }
        }

        private fun showLoader() {
            loader.visibility = View.VISIBLE
            image.visibility = View.GONE
        }

        private fun dismissLoader() {
            loader.visibility = View.GONE
            image.visibility = View.VISIBLE

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
                urlToImage,
                image.context
            ).observeOn(AndroidSchedulers.mainThread()).subscribeOn(
                Schedulers.newThread()
            ).subscribe(DownloadImageFromUrlObserver(image))
        }

        fun getBitmap(url: String?, context: Context): Observable<Bitmap> {
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
                dismissLoader()
            }
        }

    }
}