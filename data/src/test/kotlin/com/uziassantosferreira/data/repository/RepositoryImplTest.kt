package com.uziassantosferreira.data.repository

import com.nhaarman.mockitokotlin2.mock
import com.uziassantosferreira.data.datasource.DataSource
import com.uziassantosferreira.domain.model.Comment
import com.uziassantosferreira.domain.model.Pagination
import com.uziassantosferreira.domain.model.Post
import io.reactivex.Flowable
import org.amshove.kluent.When
import org.amshove.kluent.calling
import org.amshove.kluent.itReturns
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RepositoryImplTest {

    private val dataSource: DataSource = mock()

    @InjectMocks
    private lateinit var repository: RepositoryImpl

    private val postsResult = Pair(Pagination(), emptyList<Post>())
    private val commentsResult = Pair(Pagination(), emptyList<Comment>())

    @Before
    fun `set up mocks`() {
        When calling dataSource.getPostsByCommunity() itReturns Flowable.just(postsResult)
        When calling dataSource.getCommentsByCommunityAndId() itReturns Flowable.just(commentsResult)
    }

    @Test
    fun `should be call data source when get posts by community and expected empty pair`() {
        repository.getPostsByCommunity()
            .test()
            .assertValue(postsResult)
            .assertComplete()
            .assertNoErrors()
    }

    @Test
    fun `should be call data source when get comments by community and id and expected empty pair`() {
        repository.getCommentsByCommunityAndId()
            .test()
            .assertValue(commentsResult)
            .assertComplete()
            .assertNoErrors()
    }
}