package com.uziassantosferreira.domain.usecase

import com.uziassantosferreira.domain.exception.RequestValueNullPointException
import com.uziassantosferreira.domain.model.Pagination
import com.uziassantosferreira.domain.model.Post
import com.uziassantosferreira.domain.repository.Repository
import com.uziassantosferreira.domain.requestvalue.GetPostByCommunityRequestValue
import io.reactivex.Flowable

class GetPostByCommunity(private val repository: Repository): UseCase<GetPostByCommunityRequestValue, Pair<Pagination, List<Post>>>() {
    override fun executeUseCase(requestValues: GetPostByCommunityRequestValue?): Flowable<Pair<Pagination, List<Post>>> {
        if (requestValues == null){ throw RequestValueNullPointException() }
        return repository.getPostsByCommunity(requestValues.community, requestValues.page)
    }
}