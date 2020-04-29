package com.example.application.news

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.application.R
import com.example.application.databinding.ArticleBinding
import com.example.application.vo.Article

class NewsListAdapter: ListAdapter<Article, NewsListAdapter.ViewHolder>(NewsDiffCallback()) {

    inner class ViewHolder(private val binding: ArticleBinding):
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Article) {

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        LayoutInflater.from(parent.context)
                .inflate(R.layout.article, parent, false)
        return createViewHolderFrom(parent)
    }

    private fun createViewHolderFrom(parent: ViewGroup): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ArticleBinding.inflate(
            layoutInflater, parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class NewsDiffCallback: DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            TODO("Not yet implemented")
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            TODO("Not yet implemented")
        }

    }
}