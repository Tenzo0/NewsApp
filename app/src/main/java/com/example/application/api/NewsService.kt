/*
 * Copyright (c) Barykin Alexey, 2020
 */

package com.example.application.api

import com.example.application.vo.NewsPage
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

const val NEWS_ENDPOINT = "https://newsapi.org/v2/"
private const val NEWS_API_KEY = "e28a85f48b6446aaa50931d0fa2b06dc"
private const val API_KEY_HEADER = "X-Api-Key: $NEWS_API_KEY"

interface NewsApi {

    @Headers(API_KEY_HEADER)
    @GET("top-headlines")
    suspend fun getNewsPage(
        @Query("country") country: String,
        @Query("pageSize") pageSize: Int,
        @Query("page") page: Int) : Response<NewsPage>
}