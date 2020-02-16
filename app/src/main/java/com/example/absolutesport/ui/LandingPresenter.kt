package com.example.absolutesport.ui

import com.example.absolutesport.network.Article
import com.example.absolutesport.repository.NetworkRepository
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.logging.Logger

class LandingPresenter : ILandingMvp.Presenter {

    private lateinit var view: ILandingMvp.View

    val networkRepository = NetworkRepository()
    override fun getTopHeadlines(){
        networkRepository.getTopHeadlines().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe(TopHeadlinesObserver(view))
    }

    override fun attach(view: ILandingMvp.View) {
        this.view = view
    }

    override fun detach() {
        Logger.getAnonymousLogger().info("detach")
    }

    class TopHeadlinesObserver(val view: ILandingMvp.View) : SingleObserver<List<Article>> {
        override fun onSuccess(t: List<Article>) {
            view.displayArtivles(t)
        }

        override fun onSubscribe(d: Disposable) {
            Logger.getAnonymousLogger().info("${d.toString()} subscribed")
        }

        override fun onError(e: Throwable) {
            Logger.getAnonymousLogger().info(e.message)
        }
    }

}