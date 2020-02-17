package com.example.absolutesport.ui

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

class ArticleListAdapter(val articles: List<Article>) :
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

        fun display(article: Article) {
            titleTextView.setText(article.title)
            if (article.urlToImage != null) {
                getBitmap(
                    article.urlToImage,
                    image.context
                ).observeOn(AndroidSchedulers.mainThread()).subscribeOn(
                    Schedulers.newThread()
                ).subscribe(DownloadImageFromUrlObserver(article, image))
            }
        }

        fun getBitmap(url: String, context: Context): Observable<Bitmap> {
            return Observable.fromCallable {
                val inputStream = URI(url).toURL().openStream()
                val image: Bitmap = BitmapFactory.decodeStream(inputStream)
                return@fromCallable (image)
            }
        }
    }

    class DownloadImageFromUrlObserver(
        val article: Article,
        val image: AppCompatImageView
    ) : Observer<Bitmap> {

        override fun onNext(bitmapFromUrl: Bitmap) {
            image.setImageBitmap(bitmapFromUrl)
        }

        override fun onSubscribe(d: Disposable) {
            Log.d("DownloadImageFromUrl", "subcribed $d")
        }

        override fun onError(e: Throwable) {
            Log.e("DownloadImageFromUrl", e.message)
        }

        override fun onComplete() {
            Log.d("DownloadImageFromUrl", "on complete")
        }
    }
}