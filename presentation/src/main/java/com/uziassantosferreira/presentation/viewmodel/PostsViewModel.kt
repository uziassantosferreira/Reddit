package com.uziassantosferreira.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.uziassantosferreira.presentation.data.datasource.NetworkState
import com.uziassantosferreira.presentation.data.repository.PostsRepository
import com.uziassantosferreira.presentation.model.Post

class PostsViewModel(private val repository: PostsRepository) : BaseViewModel() {

    init {
        repository.setCompositeDisposable(compositeDisposable)
    }

    fun getPosts(): LiveData<PagedList<Post>> = repository.getList()

    fun getNetworkState(): LiveData<NetworkState> = repository.getNetworkState()

    fun retry() = repository.retry()

    fun refresh() = repository.refresh()
}