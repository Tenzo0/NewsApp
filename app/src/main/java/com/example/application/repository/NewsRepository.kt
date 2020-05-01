/*
 * Copyright (c) Barykin Alexey, 2020
 */

package com.example.application.repository

import androidx.lifecycle.MutableLiveData
import com.example.application.api.NewsApi
import com.example.application.domain.DetailedArticle
import com.example.application.vo.asDomainObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val newsNetwork: NewsApi
) {

    val news = MutableLiveData<List<DetailedArticle>>()
    private val lastDownloadedPage = MutableLiveData<Int>()

    init {
        news.value = emptyList()
        lastDownloadedPage.value = 0
    }

    suspend fun reloadNews() {
        withContext(Dispatchers.IO) {
            val downloadedList = getNewsPage(1)
            if (!downloadedList.isNullOrEmpty()) {
                lastDownloadedPage.postValue(1)
                news.postValue(downloadedList)
            }
        }
    }

    @Synchronized
    suspend fun downloadNewsPage() {
        withContext(Dispatchers.IO) {
            val nextPage = lastDownloadedPage.value!! + 1

            val downloadedList = getNewsPage(nextPage)
            if (!downloadedList.isNullOrEmpty()) {
                lastDownloadedPage.postValue(nextPage)
                news.postValue(news.value!!.plus(downloadedList))
            }
        }
    }


    private suspend fun getNewsPage(page: Int) = newsNetwork
        .getNewsPage(
            "us",
            DEFAULT_NEWS_COUNT_PER_PAGE,
            page
        ).body()!!.articles.asDomainObject()


    companion object {
        private const val DEFAULT_NEWS_COUNT_PER_PAGE = 10
    }
}