package com.uziassantosferreira.reddit.di

import androidx.paging.PagedList
import com.uziassantosferreira.domain.model.Pagination
import com.uziassantosferreira.domain.model.Post
import com.uziassantosferreira.domain.requestvalue.GetPostByCommunityRequestValue
import com.uziassantosferreira.domain.usecase.GetPostByCommunity
import com.uziassantosferreira.domain.usecase.UseCase
import com.uziassantosferreira.presentation.data.datasource.PostsDataSourceFactory
import com.uziassantosferreira.presentation.viewmodel.PostsViewModel
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val postsModule = module {

    single<UseCase<GetPostByCommunityRequestValue, Pair<Pagination, List<Post>>>> { GetPostByCommunity(get()) }

    single { PostsDataSourceFactory(get()) }

    viewModel { (config : PagedList.Config) -> PostsViewModel(get(), config) }
}