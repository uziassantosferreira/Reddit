package com.uziassantosferreira.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.uziassantosferreira.presentation.data.datasource.NetworkState
import com.uziassantosferreira.presentation.data.repository.CommentsRepository
import com.uziassantosferreira.presentation.model.Comment
import com.uziassantosferreira.presentation.model.Post

class CommentsViewModel(private val repository: CommentsRepository): BaseViewModel() {

    init {
        repository.setCompositeDisposable(compositeDisposable)
        resetList()
    }

    private fun resetList() = repository.resetList()

    fun getComments(post: Post): LiveData<PagedList<Comment>> = repository.getList(post.remoteId)

    fun getNetworkState(): LiveData<NetworkState> = repository.getNetworkState()

    fun retry() = repository.retry()
}