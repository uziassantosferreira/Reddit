package com.uziassantosferreira.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.uziassantosferreira.presentation.data.datasource.NetworkState
import com.uziassantosferreira.presentation.data.repository.PostsRepository
import com.uziassantosferreira.presentation.model.Post

class PostsViewModel(private val postsRepository: PostsRepository) : BaseViewModel() {

    init {
        postsRepository.setCompositeDisposable(compositeDisposable)
    }

    fun getPosts(): LiveData<PagedList<Post>> = postsRepository.getPosts()

    fun getNetworkState(): LiveData<NetworkState> = postsRepository.getNetworkState()

    fun retry() = postsRepository.retry()

    fun refresh() = postsRepository.refresh()
}