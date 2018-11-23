package com.uziassantosferreira.data.datasource

import com.uziassantosferreira.data.api.RedditService
import com.uziassantosferreira.domain.model.Post
import io.reactivex.Flowable

class DataSourceImpl(private val service: RedditService): DataSource {

    override fun getPostsByCommunity(community: String): Flowable<List<Post>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}