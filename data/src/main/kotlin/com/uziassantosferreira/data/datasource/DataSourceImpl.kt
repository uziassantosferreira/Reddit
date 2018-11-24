package com.uziassantosferreira.data.datasource

import com.uziassantosferreira.data.api.RedditService
import com.uziassantosferreira.domain.model.Pagination
import com.uziassantosferreira.domain.model.Post
import io.reactivex.Flowable

class DataSourceImpl(private val service: RedditService): DataSource {

    override fun getPostsByCommunity(community: String, page: String): Flowable<Pair<Pagination, List<Post>>> =
        service.getPostsByCommunity(community, page)
            .map { Pair(Pagination(), emptyList<Post>()) }
}