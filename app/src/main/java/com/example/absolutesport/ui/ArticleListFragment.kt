package com.example.absolutesport.ui

import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.absolutesport.R
import com.example.absolutesport.Topic
import com.example.absolutesport.network.Article
import io.reactivex.disposables.CompositeDisposable
import java.io.ByteArrayOutputStream

class ArticleListFragment(private val topic: Topic) : Fragment(), IArticleListMvp.View {

    val compositeDisposable = CompositeDisposable()
    val presenter: IArticleListMvp.Presenter = ArticleListPresenter()
    lateinit var articlesRecyclerView: RecyclerView

    companion object {
        fun newInstance(topic: Topic): ArticleListFragment {
            return ArticleListFragment(topic)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_articles_list, container, false)
        articlesRecyclerView = view.findViewById(R.id.recycler_article_list)
        presenter.attach(this)
        presenter.getTopHeadlines(topic)
        return view
    }

    override fun onStart() {
        super.onStart()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    /*
    override fun onResume() {
        super.onResume()
        displayScreen()
    }*/

    override fun onAttachFragment(childFragment: Fragment) {
        presenter.attach(this)
    }

    override fun displayScreen() {

    }

    override fun showLoading() {
        Log.d(ArticleListActivity::class.simpleName, "loading")
    }

    override fun displayArticles(articles: List<Article>) {
        if (!articles.isEmpty()) {
            val adapter = ArticleListAdapter(articles)
            compositeDisposable.add(adapter.getClickedArticle().subscribe({
                cacheArticleImage(it)

                startActivity(
                    ArticleViewArticleActivity.getStartIntent(
                        articlesRecyclerView.context,
                        it
                    )
                )
            }, {
                Log.e(ArticleListActivity::class.simpleName, it.message)
            }))

            articlesRecyclerView.adapter = adapter
            articlesRecyclerView.layoutManager = LinearLayoutManager(this.context)

            articlesRecyclerView.addItemDecoration(
                DividerItemDecoration(
                    articlesRecyclerView.context,
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
                this.context?.openFileOutput(article.hashCode().toString(), Context.MODE_PRIVATE)
            fileOutputStream?.write(byteArrayOutputStream.toByteArray())
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

