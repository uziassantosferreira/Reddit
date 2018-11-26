package com.uziassantosferreira.presentation.data.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.uziassantosferreira.domain.model.Pagination
import com.uziassantosferreira.domain.requestvalue.GetPostByCommunityRequestValue
import com.uziassantosferreira.domain.usecase.UseCase
import com.uziassantosferreira.presentation.model.Post
import io.reactivex.disposables.CompositeDisposable
import kotlin.properties.Delegates

class PostsDataSourceFactory(private val getPostByCommunity:
                             UseCase<GetPostByCommunityRequestValue,
                                     Pair<Pagination, List<com.uziassantosferreira.domain.model.Post>>>)
    : DataSource.Factory<Pagination, Post>() {

    var compositeDisposable: CompositeDisposable by Delegates.notNull()

    val postsDataSourceLiveData = MutableLiveData<PostsDataSource>()

    override fun create(): DataSource<Pagination, Post> {
        val usersDataSource = PostsDataSource(
            getPostByCommunity,
            compositeDisposable
        )
        postsDataSourceLiveData.postValue(usersDataSource)
        return usersDataSource
    }

}