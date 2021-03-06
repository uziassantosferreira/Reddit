package com.uziassantosferreira.domain.repository

import com.uziassantosferreira.domain.model.Comment
import com.uziassantosferreira.domain.model.Pagination
import com.uziassantosferreira.domain.model.Post
import io.reactivex.Flowable

interface Repository {
    fun getPostsByCommunity(community: String = "",
                            page: String = ""): Flowable<Pair<Pagination, List<Post>>>
    fun getCommentsByCommunityAndId(community: String = "",
                                    remoteId: String = "",
                                    page: String = ""): Flowable<Pair<Pagination, List<Comment>>>
}