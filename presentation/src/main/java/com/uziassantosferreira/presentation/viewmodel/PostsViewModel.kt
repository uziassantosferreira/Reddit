package com.uziassantosferreira.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.uziassantosferreira.presentation.data.NetworkState
import com.uziassantosferreira.presentation.data.datasource.PostsDataSource
import com.uziassantosferreira.presentation.data.datasource.PostsDataSourceFactory
import com.uziassantosferreira.presentation.model.Post

class PostsViewModel(private val postsDataSourceFactory: PostsDataSourceFactory,
                     pagedListConfig: PagedList.Config) : BaseViewModel() {

    var postsLiveData: LiveData<PagedList<Post>>
        private set

    init {
        postsDataSourceFactory.compositeDisposable = compositeDisposable
        postsLiveData = LivePagedListBuilder<String, Post>(postsDataSourceFactory, pagedListConfig).build()
    }

    fun retry() {
        postsDataSourceFactory.postsDataSourceLiveData.value?.retry()
    }

    fun refresh() {
        postsDataSourceFactory.postsDataSourceLiveData.value?.invalidate()
    }

    fun getNetworkState(): LiveData<NetworkState> = Transformations.switchMap<PostsDataSource, NetworkState>(
        postsDataSourceFactory.postsDataSourceLiveData
    ) { it.networkState }

}