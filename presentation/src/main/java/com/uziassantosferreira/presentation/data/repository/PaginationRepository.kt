package com.uziassantosferreira.presentation.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.uziassantosferreira.presentation.data.datasource.NetworkState
import io.reactivex.disposables.CompositeDisposable

interface PaginationRepository<T> {

    fun getList(): LiveData<PagedList<T>>

    fun getNetworkState(): LiveData<NetworkState>

    fun retry()

    fun refresh()

    fun setCompositeDisposable(disposable: CompositeDisposable)
}