package com.uziassantosferreira.data.datasource

import com.uziassantosferreira.domain.model.Pagination
import com.uziassantosferreira.domain.model.Post
import io.reactivex.Flowable

interface DataSource {
    fun getPostsByCommunity(community: String = "",
                            page: String = ""): Flowable<Pair<Pagination, List<Post>>>
}