package com.uziassantosferreira.presentation.data.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.uziassantosferreira.domain.usecase.GetPostByCommunity
import com.uziassantosferreira.presentation.model.Post
import io.reactivex.disposables.CompositeDisposable

class PostsDataSourceFactory(private val getPostByCommunity: GetPostByCommunity)
    : DataSource.Factory<String, Post>() {

    val postsDataSourceLiveData = MutableLiveData<PostsDataSource>()
    lateinit var compositeDisposable: CompositeDisposable

    override fun create(): DataSource<String, Post> {
        val usersDataSource = PostsDataSource(
            getPostByCommunity,
            compositeDisposable
        )
        postsDataSourceLiveData.postValue(usersDataSource)
        return usersDataSource
    }

}