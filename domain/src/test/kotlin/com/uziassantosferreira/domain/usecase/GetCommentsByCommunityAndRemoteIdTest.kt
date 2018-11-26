package com.uziassantosferreira.domain.usecase

import com.nhaarman.mockitokotlin2.mock
import com.uziassantosferreira.domain.exception.RequestValueNullPointException
import com.uziassantosferreira.domain.model.Comment
import com.uziassantosferreira.domain.model.Pagination
import com.uziassantosferreira.domain.repository.Repository
import com.uziassantosferreira.domain.requestvalue.GetCommentsByCommunityAndRemoteIdRequestValue
import io.reactivex.Flowable
import org.amshove.kluent.When
import org.amshove.kluent.calling
import org.amshove.kluent.itReturns
import org.amshove.kluent.shouldThrow
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetCommentsByCommunityAndRemoteIdTest {

    private val repository: Repository = mock()

    private val result = Pair(Pagination(), emptyList<Comment>())

    @InjectMocks
    private lateinit var useCase: GetCommentsByCommunityAndRemoteId

    @Before
    fun `set up mocks`() {
        When calling repository.getCommentsByCommunityAndId() itReturns Flowable.just(result)
    }

    @Test
    fun `execute use case and expected success`() {
        useCase.executeUseCase(GetCommentsByCommunityAndRemoteIdRequestValue())
            .test()
            .assertValue(result)
            .assertComplete()
            .assertNoErrors()
    }

    @Test
    fun `execute use case no passing request value and expected failure`() {
        val result = { useCase.executeUseCase().test()}
        result shouldThrow RequestValueNullPointException::class
    }

}
