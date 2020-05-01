/*
 * Copyright (c) Barykin Alexey, 2020
 */

package com.example.application.news

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Html
import android.text.method.LinkMovementMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide

import com.example.application.R
import com.example.application.databinding.DetailedArticleFragmentBinding
import timber.log.Timber


class DetailedArticleFragment : Fragment() {

    private val safeArgs: DetailedArticleFragmentArgs by navArgs()
    private lateinit var binding: DetailedArticleFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.detailed_article_fragment,
            container, false)

        val detailedArticle = safeArgs.detailedArticle

        with(binding) {
            title.text = detailedArticle.title
            content.text = detailedArticle.content
            Glide.with(binding.root)
                .load(detailedArticle.imgUrl)
                //.placeholder(ColorDrawable(Color.GRAY))
                .into(imageView)

            sourceLink.apply {
                text = Html.fromHtml("<a href=${detailedArticle.url}>${getString(R.string.source_link)}</a>")
                movementMethod = LinkMovementMethod.getInstance()
            }
        }

        requireActivity().actionBar?.setDisplayHomeAsUpEnabled(true)

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.i("ok")
    }

}
