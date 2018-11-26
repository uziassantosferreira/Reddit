package com.uziassantosferreira.presentation.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.uziassantosferreira.domain.model.Pagination
import com.uziassantosferreira.presentation.data.datasource.CommentsDataSource
import com.uziassantosferreira.presentation.data.datasource.CommentsDataSourceFactory
import com.uziassantosferreira.presentation.data.datasource.NetworkState
import com.uziassantosferreira.presentation.model.Comment
import io.reactivex.disposables.CompositeDisposable
import kotlin.properties.Delegates

class CommentsRepositoryImpl(
    private val factory: CommentsDataSourceFactory,
    pagedListConfig: PagedList.Config): CommentsRepository {

    private var comments: LiveData<PagedList<Comment>> by Delegates.notNull()

    init {
        comments = LivePagedListBuilder(factory, pagedListConfig).build()
    }

    override fun getList(remoteId: String): LiveData<PagedList<Comment>> {
        if (CommentsDataSource.remoteId != remoteId){
            refresh()
            CommentsDataSource.remoteId = remoteId
        }
        return comments
    }
    override fun getList(): LiveData<PagedList<Comment>> {
        TODO()
    }

    override fun getNetworkState(): LiveData<NetworkState> =
        Transformations.switchMap(factory.dataSource) { it.networkState }

    override fun retry() {
        factory.dataSource.value?.retry()
    }

    override fun refresh() {
        factory.dataSource.value?.invalidate()
    }

    override fun setCompositeDisposable(disposable: CompositeDisposable) {
        factory.compositeDisposable = disposable
    }
}