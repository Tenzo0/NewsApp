package com.example.application.di.news

import androidx.lifecycle.ViewModel
import com.example.application.di.ViewModelKey
import com.example.application.news.NewsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface NewsModule {


    @Binds
    @IntoMap
    @ViewModelKey(NewsViewModel::class)
    fun bindNewsViewModel(viewModel: NewsViewModel): ViewModel
}