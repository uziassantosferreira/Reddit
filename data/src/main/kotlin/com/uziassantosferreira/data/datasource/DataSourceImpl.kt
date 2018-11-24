package com.uziassantosferreira.data.datasource

import com.uziassantosferreira.data.api.RedditService
import com.uziassantosferreira.data.mapper.JsonPostMapper
import com.uziassantosferreira.data.model.JsonPost
import com.uziassantosferreira.domain.model.Pagination
import com.uziassantosferreira.domain.model.Post
import io.reactivex.Flowable

class DataSourceImpl(private val service: RedditService): DataSource {

    override fun getPostsByCommunity(community: String, page: String): Flowable<Pair<Pagination, List<Post>>> =
        service.getPostsByCommunity(community, page)
            .map { json ->
                val list = json.data?.children
                    ?.map { it.data }
                    ?.mapNotNull { it }

                val posts = JsonPostMapper.transformToList(list ?: emptyList())

                Pair(Pagination(json.data?.after ?: ""), posts)
            }
}