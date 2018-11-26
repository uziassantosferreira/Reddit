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
import kotlinx.android.synthetic.main.fragment_posts.*
import kotlinx.android.synthetic.main.include_posts_list.*
import kotlinx.android.synthetic.main.list_item_network_state.*
import org.koin.android.ext.android.inject
import org.koin.android.ext.android.setProperty
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class PostsFragment: BaseFragment() {

    companion object {
        const val PROPERTY_PAGED_LIST = "pagedList"
    }

    private val postsViewModel: PostsViewModel by viewModel()

    private val postsAdapter: PostsAdapter by inject{ parametersOf({postsViewModel.retry()},
        {post: Post-> clickItem(post) }) }

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

        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        buttonRetry.setOnClickListener { postsViewModel.retry() }
    }

    private fun initAdapter() {
        recyclerView.adapter = postsAdapter
    }

    private fun clickItem(post: Post) {
        val action = PostsFragmentDirections.ActionPostsFragmentToDetailFragment(post)
        navController.navigate(action)
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

    override fun setInitialLoadingState(networkState: NetworkState?) {
        super.setInitialLoadingState(networkState)
        if (swipeRefreshLayout.isRefreshing){
            swipeRefreshLayout.isRefreshing = networkState?.status == Status.RUNNING
            progressBarLoading.visibility = View.GONE
        }
        postsAdapter.setNetworkState(NetworkState.LOADED)
    }

    private fun getPagedListConfig() =
        PagedList.Config.Builder()
            .setPageSize(15)
            .setEnablePlaceholders(false)
            .build()
}