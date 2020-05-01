package com.example.application.di

import com.example.application.api.NewsApi
import com.example.application.repository.NewsRepository
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
object AppModule {

    @JvmStatic
    @Singleton
    @Provides
    fun provideNewsRepository(newsNetwork: NewsApi): NewsRepository = NewsRepository(newsNetwork)

    @Singleton
    @Provides
    fun provideIoDispatcher() = Dispatchers.IO
}