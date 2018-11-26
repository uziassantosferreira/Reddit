package com.uziassantosferreira.data.datasource

import com.uziassantosferreira.data.api.RedditService
import com.uziassantosferreira.data.errors.NetworkingErrorHandler
import com.uziassantosferreira.data.mapper.JsonCommentMapper
import com.uziassantosferreira.data.mapper.JsonPostMapper
import com.uziassantosferreira.domain.model.Comment
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
            .compose(NetworkingErrorHandler())

    override fun getCommentsByCommunityAndId(
        community: String,
        remoteId: String,
        page: String
    ): Flowable<Pair<Pagination, List<Comment>>> =
        service.getCommentsByCommunityAndId(community,remoteId, page)
            .map { json ->
                val comments = mutableListOf<Comment>()
                var after = ""
                json.forEach { genericList ->
                    val list = genericList.data?.children
                        ?.map { it.data }
                        ?.mapNotNull { it }
                    if (!genericList.data?.after.isNullOrEmpty() && after.isEmpty()){
                        after = genericList.data?.after ?: ""
                    }
                    comments.addAll(JsonCommentMapper.transformToList(list ?: emptyList()))
                }

                Pair(Pagination(after), comments)
            }
            .compose(NetworkingErrorHandler())
}