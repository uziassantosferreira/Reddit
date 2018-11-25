package com.uziassantosferreira.presentation.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.uziassantosferreira.presentation.data.datasource.NetworkState
import com.uziassantosferreira.presentation.data.datasource.PostsDataSourceFactory
import com.uziassantosferreira.presentation.model.Post
import io.reactivex.disposables.CompositeDisposable
import kotlin.properties.Delegates

class PostsRepositoryImpl(
    private val postsDataSourceFactory: PostsDataSourceFactory,
    pagedListConfig: PagedList.Config): PostsRepository {

    private var postsLiveData: LiveData<PagedList<Post>> by Delegates.notNull()

    init {
        postsLiveData = LivePagedListBuilder<String, Post>(postsDataSourceFactory, pagedListConfig).build()
    }


    override fun getPosts(): LiveData<PagedList<Post>> {
        return postsLiveData
    }

    override fun getNetworkState(): LiveData<NetworkState> =
        Transformations.switchMap(postsDataSourceFactory.postsDataSourceLiveData) { it.networkState }

    override fun retry() {
        postsDataSourceFactory.postsDataSourceLiveData.value?.retry()
    }

    override fun refresh() {
        postsDataSourceFactory.postsDataSourceLiveData.value?.invalidate()
    }

    override fun setCompositeDisposable(disposable: CompositeDisposable) {
        postsDataSourceFactory.compositeDisposable = disposable
    }
}