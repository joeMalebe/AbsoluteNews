package com.example.absolutesport.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.absolutesport.R
import com.example.absolutesport.ui.ArticleHeadingsAdapter.Companion.NUMBER_OF_HEADINGS
import kotlinx.android.synthetic.main.activity_landing.*

class LandingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing)

        view_pager_topics.adapter = ArticleHeadingsAdapter(supportFragmentManager)
        view_pager_topics.offscreenPageLimit = NUMBER_OF_HEADINGS
        tab_headings.setupWithViewPager(view_pager_topics)
    }
}
