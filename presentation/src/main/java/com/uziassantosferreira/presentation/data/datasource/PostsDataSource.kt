package com.uziassantosferreira.presentation.data.datasource

import com.uziassantosferreira.domain.model.Pagination
import com.uziassantosferreira.domain.requestvalue.GetPostByCommunityRequestValue
import com.uziassantosferreira.domain.usecase.UseCase
import com.uziassantosferreira.presentation.data.datasource.CommentsDataSource.Companion.COMMUNITY
import com.uziassantosferreira.presentation.mapper.PresentationPostMapper
import com.uziassantosferreira.presentation.model.Post as Presentation
import com.uziassantosferreira.domain.model.Post
import io.reactivex.disposables.CompositeDisposable

class PostsDataSource(
    getPostByCommunity: UseCase<GetPostByCommunityRequestValue,
            Pair<Pagination, List<Post>>>,
    compositeDisposable: CompositeDisposable
) : GenericDataSource<Post, Presentation, GetPostByCommunityRequestValue>(getPostByCommunity,
    compositeDisposable, ::getPostByCommunityRequestValue, ::getMapper)

fun getPostByCommunityRequestValue(pagination: Pagination): GetPostByCommunityRequestValue =
    GetPostByCommunityRequestValue(page = pagination.nextPage, community = COMMUNITY)


fun getMapper(list: List<Post>): List<Presentation> = PresentationPostMapper.transformFromList(list)
