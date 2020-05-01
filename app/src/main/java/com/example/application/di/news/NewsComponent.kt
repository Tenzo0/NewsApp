package com.example.application.di.news

import com.example.application.news.NewsListFragment
import dagger.Subcomponent

@Subcomponent(modules = [NewsModule::class])
interface NewsComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create() : NewsComponent
    }

    fun inject(newsListFragment: NewsListFragment)
}