package com.example.application.vo

import com.example.application.domain.DetailedArticle

data class NewsPage(
    val status: String,
    val totalResults: String,
    val articles: List<Article>
)

data class Article(
    val source: Source?,
    val author: String?,
    val title: String?,
    val description: String?,
    val url: String?,
    val urlToImage: String?,
    val publishedAt: String?,
    val content: String?
) {
    data class Source(
        val id: String?,
        val name: String?
    )
}

fun Article.asDomainObject(): DetailedArticle = DetailedArticle(
    title ?: "",
    description ?: "",
    content ?: "",
    url ?: "",
    urlToImage ?: ""
)

fun List<Article>.asDomainObject(): List<DetailedArticle> = map { it.asDomainObject() }