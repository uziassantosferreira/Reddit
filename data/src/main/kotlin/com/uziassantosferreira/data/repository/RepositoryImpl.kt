package com.uziassantosferreira.data.repository

import com.uziassantosferreira.data.datasource.DataSource
import com.uziassantosferreira.domain.model.Post
import com.uziassantosferreira.domain.repository.Repository
import io.reactivex.Flowable

class RepositoryImpl(private val dataSource: DataSource): Repository {

    override fun getPostsByCommunity(community: String): Flowable<List<Post>> =
        dataSource.getPostsByCommunity(community)
}