package com.uziassantosferreira.domain.repository

import com.uziassantosferreira.domain.model.Post
import io.reactivex.Flowable

interface Repository {
    fun getPostsByCommunity(community: String): Flowable<List<Post>>
}