/*
 * Copyright (c) Barykin Alexey, 2020
 */

package com.example.application.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.application.domain.DetailedArticle
import com.example.application.repository.NewsRepository
import com.example.application.vo.Article
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class NewsViewModel @Inject constructor(
    private val repository: NewsRepository
) : ViewModel() {

    //val lastPage = repository.lastDownloadedPage

    var newsList: LiveData<List<DetailedArticle>> = repository.news

    // Flag to show network error
    var isNetworkErrorShown = MutableLiveData<Boolean>()

    private var _isProgressLoadingShown = MutableLiveData<Boolean>()
    val isProgressLoadingShown: LiveData<Boolean>
        get() = _isProgressLoadingShown

    init {
        isNetworkErrorShown.value = false
        _isProgressLoadingShown.value = false
    }

    fun reloadNews() {
        viewModelScope.launch {
            _isProgressLoadingShown.value = true

            try {
                repository.reloadNews()
                isNetworkErrorShown.value = false
            }
            catch (e: Exception) {
                //Network error
                Timber.e("$e")
                isNetworkErrorShown.value = true
            }

            _isProgressLoadingShown.value = false
        }
    }

    fun getNextPage() {
        viewModelScope.launch {
            _isProgressLoadingShown.value = true

            try {
                repository.downloadNewsPage()
                isNetworkErrorShown.value = false
            }
            catch (e: Exception) {
                //Network error
                Timber.e("$e")
                isNetworkErrorShown.value = true
            }

            _isProgressLoadingShown.value = false
        }
    }
}
