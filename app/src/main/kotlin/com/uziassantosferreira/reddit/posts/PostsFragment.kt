package com.uziassantosferreira.reddit.posts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.uziassantosferreira.presentation.data.NetworkState
import com.uziassantosferreira.presentation.data.Status
import com.uziassantosferreira.presentation.model.Post
import com.uziassantosferreira.presentation.viewmodel.PostsViewModel
import com.uziassantosferreira.reddit.R
import com.uziassantosferreira.reddit.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_posts.*
import kotlinx.android.synthetic.main.list_item_network_state.*
import org.koin.android.ext.android.inject

class PostsFragment: BaseFragment() {

    private val postsViewModel: PostsViewModel by inject()

    private lateinit var postsAdapter: PostsAdapter

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_posts, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        initSwipeToRefresh()
    }

    private fun initAdapter() {
        postsAdapter = PostsAdapter {
            postsViewModel.retry()
        }
        recyclerView.adapter = postsAdapter
        postsViewModel.postsLiveData.observe(this,
            Observer<PagedList<Post>> { postsAdapter.submitList(it) })
        postsViewModel.getNetworkState().observe(this,
            Observer<NetworkState> { postsAdapter.setNetworkState(it) })
    }

    /**
     * Init swipe to refresh and enable pull to refresh only when there are items in the adapter
     */
    private fun initSwipeToRefresh() {
        postsViewModel.getRefreshState().observe(this, Observer { networkState ->
            if (postsAdapter.currentList != null) {
                if (postsAdapter.currentList!!.size > 0) {
                    swipeRefreshLayout.isRefreshing = networkState?.status == NetworkState.LOADING.status
                } else {
                    setInitialLoadingState(networkState)
                }
            } else {
                setInitialLoadingState(networkState)
            }
        })

        swipeRefreshLayout.setOnRefreshListener {
            postsViewModel.refresh()
        }
    }

    /**
     * Show the current network state for the first load when the user list
     * in the adapter is empty and disable swipe to scroll at the first loading
     *
     * @param networkState the new network state
     */
    private fun setInitialLoadingState(networkState: NetworkState?) {
        //error message
        errorMessageTextView.visibility = if (networkState?.message != null) View.VISIBLE else View.GONE
        if (networkState?.message != null) {
            errorMessageTextView.text = networkState.message
        }

        //loading and retry
        retryLoadingButton.visibility = if (networkState?.status == Status.FAILED) View.VISIBLE else View.GONE
        loadingProgressBar.visibility = if (networkState?.status == Status.RUNNING) View.VISIBLE else View.GONE

        swipeRefreshLayout.isEnabled = networkState?.status == Status.SUCCESS
        retryLoadingButton.setOnClickListener { postsViewModel.retry() }
    }
}