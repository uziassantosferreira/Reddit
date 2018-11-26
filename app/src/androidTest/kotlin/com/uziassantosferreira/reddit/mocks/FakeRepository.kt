package com.uziassantosferreira.reddit.mocks

import com.uziassantosferreira.domain.model.Comment
import com.uziassantosferreira.domain.model.Pagination
import com.uziassantosferreira.domain.model.Post
import com.uziassantosferreira.domain.repository.Repository
import io.reactivex.Flowable
import java.util.concurrent.TimeUnit

class FakeRepository: Repository {

    companion object {
        var fakeStatus = FakeStatus.SUCCESS
    }

    override fun getPostsByCommunity(community: String,
                                     page: String): Flowable<Pair<Pagination, List<Post>>> {
        return when(fakeStatus){
            FakeStatus.LOADING -> Flowable.interval(1000, 5000, TimeUnit.SECONDS)
                .map { Pair(Pagination(), listOf(Post())) }
            FakeStatus.SUCCESS -> Flowable.just(Pair(Pagination(), listOf(Post(title = "Test"))))
            FakeStatus.FAILURE -> Flowable.error(Exception())
        }
    }

    override fun getCommentsByCommunityAndId(
        community: String,
        remoteId: String,
        page: String
    ): Flowable<Pair<Pagination, List<Comment>>> {
        return when(fakeStatus){
            FakeStatus.LOADING -> Flowable.interval(1000, 5000, TimeUnit.SECONDS)
                .map { Pair(Pagination(), listOf(Comment())) }
            FakeStatus.SUCCESS -> Flowable.just(Pair(Pagination(), listOf(Comment(text = "Test"))))
            FakeStatus.FAILURE -> Flowable.error(Exception())
        }
    }
}