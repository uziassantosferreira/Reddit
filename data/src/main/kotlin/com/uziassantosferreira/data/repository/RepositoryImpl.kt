package com.uziassantosferreira.data.repository

import com.uziassantosferreira.data.datasource.DataSource
import com.uziassantosferreira.domain.model.Comment
import com.uziassantosferreira.domain.model.Pagination
import com.uziassantosferreira.domain.model.Post
import com.uziassantosferreira.domain.repository.Repository
import io.reactivex.Flowable

class RepositoryImpl(private val dataSource: DataSource): Repository {

    override fun getPostsByCommunity(community: String, page: String): Flowable<Pair<Pagination, List<Post>>> =
            dataSource.getPostsByCommunity(community, page)

    override fun getCommentsByCommunityAndId(
        community: String,
        remoteId: String,
        page: String
    ): Flowable<Pair<Pagination, List<Comment>>>  = dataSource.getCommentsByCommunityAndId(community, remoteId, page)
}