package com.uziassantosferreira.reddit.di

import com.uziassantosferreira.domain.model.Comment
import com.uziassantosferreira.domain.model.Pagination
import com.uziassantosferreira.domain.requestvalue.GetCommentsByCommunityAndRemoteIdRequestValue
import com.uziassantosferreira.domain.usecase.GetCommentsByCommunityAndRemoteId
import com.uziassantosferreira.domain.usecase.UseCase
import com.uziassantosferreira.presentation.data.datasource.CommentsDataSourceFactory
import com.uziassantosferreira.presentation.data.repository.CommentsRepository
import com.uziassantosferreira.presentation.data.repository.CommentsRepositoryImpl
import com.uziassantosferreira.presentation.viewmodel.CommentsViewModel
import com.uziassantosferreira.reddit.detail.CommentsAdapter
import com.uziassantosferreira.reddit.detail.DetailFragment
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val detailModule = module {

    single<UseCase<GetCommentsByCommunityAndRemoteIdRequestValue,
            Pair<Pagination, List<Comment>>>>(name = USE_CASE_COMMENT) { GetCommentsByCommunityAndRemoteId(get()) }

    single { CommentsDataSourceFactory(get(USE_CASE_COMMENT)) }

    single<CommentsRepository> { CommentsRepositoryImpl(get(), getProperty(DetailFragment.PROPERTY_PAGED_LIST)) }

    factory { (retryCallback: () -> Unit) -> CommentsAdapter(retryCallback) }

    viewModel { CommentsViewModel(get()) }
}

private const val USE_CASE_COMMENT = "useCaseComment"