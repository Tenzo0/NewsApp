package com.example.application.news

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View.GONE
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.application.R
import com.example.application.databinding.NewsListItemBinding
import com.example.application.domain.DetailedArticle
import timber.log.Timber


class NewsListAdapter (
    private val context: Context,
    private val clickListener: NewsItemClickListener,
    private val viewModel: NewsViewModel
)
    : ListAdapter<DetailedArticle, NewsListAdapter.ViewHolder>(NewsDiffCallback()
) {

    inner class ViewHolder(private val binding: NewsListItemBinding):
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: DetailedArticle) {
            with(binding) {
                article = item
                newsItemClickListener = clickListener

                if (item.imgUrl.isNotBlank()) {
                    Glide.with(binding.root)
                        .load(item.imgUrl)
                        .fitCenter()
                        .placeholder(ContextCompat.getDrawable(context, R.drawable.ic_image_placeholder))
                        .into(image)
                }
                else image.visibility = GONE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        LayoutInflater.from(parent.context)
                .inflate(R.layout.news_list_item, parent, false)
        return createViewHolderFrom(parent)
    }

    private fun createViewHolderFrom(parent: ViewGroup): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = NewsListItemBinding.inflate(
            layoutInflater, parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onViewAttachedToWindow(holder: ViewHolder) {
        super.onViewAttachedToWindow(holder)

        if (holder.layoutPosition + 1 == itemCount) {
            viewModel.getNextPage()
        }
        Timber.i("${holder.layoutPosition + 1} $itemCount")
    }

    class NewsItemClickListener(val clickListener: (article : DetailedArticle) -> Unit) {
        fun onClick(article: DetailedArticle) = clickListener(article)
    }

    class NewsDiffCallback: DiffUtil.ItemCallback<DetailedArticle>() {
        override fun areItemsTheSame(oldItem: DetailedArticle, newItem: DetailedArticle): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: DetailedArticle, newItem: DetailedArticle): Boolean {
            return oldItem == newItem
        }

    }
}