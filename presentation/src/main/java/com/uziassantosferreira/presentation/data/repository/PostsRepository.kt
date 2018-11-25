package com.uziassantosferreira.presentation.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.uziassantosferreira.presentation.data.datasource.NetworkState
import com.uziassantosferreira.presentation.model.Post
import io.reactivex.disposables.CompositeDisposable

interface PostsRepository {

    fun getPosts(): LiveData<PagedList<Post>>

    fun getNetworkState(): LiveData<NetworkState>

    fun retry()

    fun refresh()

    fun setCompositeDisposable(disposable: CompositeDisposable)
}