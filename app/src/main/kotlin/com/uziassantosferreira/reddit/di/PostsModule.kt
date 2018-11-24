package com.uziassantosferreira.reddit.di

import androidx.paging.PagedList
import com.uziassantosferreira.domain.usecase.GetPostByCommunity
import com.uziassantosferreira.presentation.data.datasource.PostsDataSourceFactory
import com.uziassantosferreira.presentation.viewmodel.PostsViewModel
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val postsModule = module {

    single { GetPostByCommunity(get()) }

    single { PostsDataSourceFactory(get()) }

    viewModel { (config : PagedList.Config) -> PostsViewModel(get(), config) }
}