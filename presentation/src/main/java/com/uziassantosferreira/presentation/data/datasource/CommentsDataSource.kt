package com.uziassantosferreira.presentation.data.datasource

import com.uziassantosferreira.domain.model.Comment
import com.uziassantosferreira.presentation.model.Comment as Presentation
import com.uziassantosferreira.domain.model.Pagination
import com.uziassantosferreira.domain.requestvalue.GetCommentsByCommunityAndRemoteIdRequestValue
import com.uziassantosferreira.domain.usecase.UseCase
import com.uziassantosferreira.presentation.data.datasource.CommentsDataSource.Companion.COMMUNITY
import com.uziassantosferreira.presentation.data.datasource.CommentsDataSource.Companion.remoteId
import com.uziassantosferreira.presentation.mapper.PresentationCommentMapper
import io.reactivex.disposables.CompositeDisposable
class CommentsDataSource(
    getCommentsByCommunityAndRemoteId: UseCase<GetCommentsByCommunityAndRemoteIdRequestValue,
            Pair<Pagination, List<Comment>>>,
    compositeDisposable: CompositeDisposable
) : GenericDataSource<Comment, Presentation,
        GetCommentsByCommunityAndRemoteIdRequestValue>(getCommentsByCommunityAndRemoteId,
    compositeDisposable, ::getCommentsByCommunityAndRemoteIdRequestValue, ::getMapper) {

    companion object {
        //TODO in the future view can be pass others community, backend offer support
        const val COMMUNITY = "r/Android"
        var remoteId = ""
    }

}

fun getCommentsByCommunityAndRemoteIdRequestValue(pagination: Pagination): GetCommentsByCommunityAndRemoteIdRequestValue =
    GetCommentsByCommunityAndRemoteIdRequestValue(page = pagination.nextPage,
        remoteId = remoteId, community = COMMUNITY)

fun getMapper(list: List<Comment>) = PresentationCommentMapper.transformFromList(list)

