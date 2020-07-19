package com.example.absolutesport.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.absolutesport.Topic

class ArticleHeadingsAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(
    fragmentManager,
    BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            Topic.BUSINESS.position -> ArticleListFragment.newInstance(Topic.BUSINESS)
            Topic.SA_NEWS.position -> ArticleListFragment.newInstance(Topic.SA_NEWS)
            Topic.SPORTS.position -> ArticleListFragment.newInstance(Topic.SPORTS)
            Topic.SCIENCE.position -> ArticleListFragment.newInstance(Topic.SCIENCE)
            Topic.ENTERTAINMENT.position -> ArticleListFragment.newInstance(Topic.ENTERTAINMENT)
            Topic.GENERAL.position -> ArticleListFragment.newInstance(Topic.GENERAL)
            Topic.HEALTH.position -> ArticleListFragment.newInstance(Topic.HEALTH)
            Topic.TECHNOLOGY.position -> ArticleListFragment.newInstance(Topic.TECHNOLOGY)
            else ->
                throw IllegalArgumentException("the position $position is invalid")
        }
    }

    override fun getCount(): Int {
        return Companion.NUMBER_OF_HEADINGS
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            Topic.TECHNOLOGY.position -> Topic.TECHNOLOGY.category
            Topic.SA_NEWS.position -> Topic.SA_NEWS.category
            Topic.SCIENCE.position -> Topic.SCIENCE.category
            Topic.GENERAL.position -> Topic.GENERAL.category
            Topic.BUSINESS.position -> Topic.BUSINESS.category
            Topic.SPORTS.position -> Topic.SPORTS.category
            Topic.HEALTH.position -> Topic.HEALTH.category
            Topic.ENTERTAINMENT.position -> Topic.ENTERTAINMENT.category
            else -> throw IllegalArgumentException("the position $position is invalid")
        }
    }

    companion object {
        const val NUMBER_OF_HEADINGS: Int = 7
    }
}
