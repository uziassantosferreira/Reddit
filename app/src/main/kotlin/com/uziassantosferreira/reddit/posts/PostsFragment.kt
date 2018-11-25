package com.uziassantosferreira.reddit.posts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.uziassantosferreira.presentation.data.datasource.NetworkState
import com.uziassantosferreira.presentation.data.datasource.Status
import com.uziassantosferreira.presentation.model.Post
import com.uziassantosferreira.presentation.viewmodel.PostsViewModel
import com.uziassantosferreira.reddit.R
import com.uziassantosferreira.reddit.base.BaseFragment
import com.uziassantosferreira.reddit.extension.getMessage
import kotlinx.android.synthetic.main.fragment_posts.*
import kotlinx.android.synthetic.main.list_item_network_state.*
import org.koin.android.ext.android.setProperty
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class PostsFragment: BaseFragment() {

    companion object {
        const val PROPERTY_PAGED_LIST = "pagedList"
    }

    private val postsViewModel: PostsViewModel by viewModel()

    private lateinit var postsAdapter: PostsAdapter

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        setProperty(PROPERTY_PAGED_LIST, getPagedListConfig())
        return inflater.inflate(R.layout.fragment_posts, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        initSwipeToRefresh()
        subscribeLiveData()
    }

    private fun initAdapter() {
        postsAdapter = PostsAdapter {
            postsViewModel.retry()
        }
        recyclerView.adapter = postsAdapter
    }

    private fun initSwipeToRefresh() {
        swipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(requireContext(), R.color.colorAccent))
        swipeRefreshLayout.setOnRefreshListener {
            postsViewModel.refresh()
        }
    }

    private fun subscribeLiveData() {
        postsViewModel.getPosts().observe(this,
            Observer<PagedList<Post>> { postsAdapter.submitList(it) })
        postsViewModel.getNetworkState().observe(this,
            Observer<NetworkState> {
                if (postsAdapter.currentList.isNullOrEmpty() || swipeRefreshLayout.isRefreshing){
                    setInitialLoadingState(it)
                }else {
                    postsAdapter.setNetworkState(it)
                }
            })
    }

    private fun setInitialLoadingState(networkState: NetworkState?) {
        errorMessageTextView.visibility = if (networkState?.failure != null) View.VISIBLE else View.GONE
        networkState?.failure?.let {
            errorMessageTextView.text = it.getMessage(requireContext())
        }

        retryLoadingButton.visibility = if (networkState?.status == Status.FAILED) View.VISIBLE else View.GONE

        if (swipeRefreshLayout.isRefreshing){
            swipeRefreshLayout.isRefreshing = networkState?.status == Status.RUNNING
            loadingProgressBar.visibility = View.GONE
        }else{
            loadingProgressBar.visibility = if (networkState?.status == Status.RUNNING) View.VISIBLE else View.GONE
        }

        postsAdapter.setNetworkState(NetworkState.LOADED)
        retryLoadingButton.setOnClickListener { postsViewModel.retry() }
    }

    private fun getPagedListConfig() =
        PagedList.Config.Builder()
            .setPageSize(1)
            .setInitialLoadSizeHint(1)
            .setEnablePlaceholders(false)
            .build()
}