package com.uziassantosferreira.domain.usecase

import com.uziassantosferreira.domain.exception.RequestValueNullPointException
import com.uziassantosferreira.domain.model.Comment
import com.uziassantosferreira.domain.model.Pagination
import com.uziassantosferreira.domain.repository.Repository
import com.uziassantosferreira.domain.requestvalue.GetCommentsByCommunityAndRemoteIdRequestValue
import io.reactivex.Flowable

class GetCommentsByCommunityAndRemoteId(private val repository: Repository):
    UseCase<GetCommentsByCommunityAndRemoteIdRequestValue, Pair<Pagination, List<Comment>>>() {

    override fun executeUseCase(requestValues: GetCommentsByCommunityAndRemoteIdRequestValue?):
            Flowable<Pair<Pagination, List<Comment>>> {
        if (requestValues == null){ throw RequestValueNullPointException() }
        return repository.getCommentsByCommunityAndId(requestValues.community,
            requestValues.remoteId, requestValues.page)
    }
}