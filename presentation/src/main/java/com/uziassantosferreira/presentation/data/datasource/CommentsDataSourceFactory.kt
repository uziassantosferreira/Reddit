package com.uziassantosferreira.presentation.data.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.uziassantosferreira.domain.model.Pagination
import com.uziassantosferreira.domain.requestvalue.GetCommentsByCommunityAndRemoteIdRequestValue
import com.uziassantosferreira.domain.usecase.UseCase
import com.uziassantosferreira.presentation.model.Comment
import io.reactivex.disposables.CompositeDisposable
import kotlin.properties.Delegates

class CommentsDataSourceFactory(private val getCommentsByCommunityAndRemoteId:
                             UseCase<GetCommentsByCommunityAndRemoteIdRequestValue,
                                     Pair<Pagination, List<com.uziassantosferreira.domain.model.Comment>>>)
    : DataSource.Factory<Pagination, Comment>() {

    var compositeDisposable: CompositeDisposable by Delegates.notNull()

    val dataSource = MutableLiveData<CommentsDataSource>()

    override fun create(): DataSource<Pagination, Comment> {
        val dataSource = CommentsDataSource(getCommentsByCommunityAndRemoteId, compositeDisposable)
        this.dataSource.postValue(dataSource)
        return dataSource
    }

}