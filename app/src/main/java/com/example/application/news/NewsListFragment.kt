/*
 * Copyright (c) Barykin Alexey, 2020
 */

package com.example.application.news

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.application.App
import com.example.application.R
import com.example.application.databinding.NewsListFragmentBinding
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_LONG
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.news_list_fragment.*
import javax.inject.Inject

class NewsListFragment : Fragment() {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<NewsViewModel> { viewModelFactory }
    private lateinit var binding: NewsListFragmentBinding
    private lateinit var newsListAdapter: NewsListAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (requireActivity().application as App).appComponent
            .newsComponent().create().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater,
            R.layout.news_list_fragment, container, false)

        binding.swipeRefreshLayout.isRefreshing = false

        setupList()
        setupViewModel()

        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.reloadNews()
        }

        return binding.root
    }

    private fun setupList() {
        newsListAdapter = NewsListAdapter(
            requireContext(),
            NewsListAdapter.NewsItemClickListener {
                findNavController().navigate(
                    NewsListFragmentDirections
                        .actionNewsListFragmentToDetailedArticleFragment(it)
                )
            },
            viewModel
        )

        with(binding.newsList) {
            adapter = newsListAdapter
        }
    }

    private fun setupViewModel() {
        with(viewModel) {
            viewModel.isNetworkErrorShown.observe(viewLifecycleOwner, Observer {
                if (it == true) {
                    Snackbar.make(binding.root, getString(R.string.network_error), LENGTH_LONG)
                        .setAction(getString(R.string.retry)) { viewModel.getNextPage() }
                        .show()
                    viewModel.isNetworkErrorShown.value = false
                }
            })

            viewModel.isProgressLoadingShown.observe(viewLifecycleOwner, Observer {
                binding.swipeRefreshLayout.isRefreshing = it
            })

            newsList.observe(viewLifecycleOwner, Observer {
                newsListAdapter.submitList(it)
            })

            if (viewModel.newsList.value!!.isEmpty()) getNextPage()
        }
    }
}
