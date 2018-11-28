package com.uziassantosferreira.reddit.di

import android.widget.ImageView
import com.uziassantosferreira.domain.model.Pagination
import com.uziassantosferreira.domain.model.Post
import com.uziassantosferreira.domain.requestvalue.GetPostByCommunityRequestValue
import com.uziassantosferreira.domain.usecase.GetPostByCommunity
import com.uziassantosferreira.domain.usecase.UseCase
import com.uziassantosferreira.presentation.data.datasource.PostsDataSourceFactory
import com.uziassantosferreira.presentation.data.repository.PostsRepository
import com.uziassantosferreira.presentation.data.repository.PostsRepositoryImpl
import com.uziassantosferreira.presentation.viewmodel.PostsViewModel
import com.uziassantosferreira.reddit.posts.PostsAdapter
import com.uziassantosferreira.reddit.posts.PostsFragment
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val postsModule = module {

    single<UseCase<GetPostByCommunityRequestValue, Pair<Pagination, List<Post>>>>(USE_CASE_POST) { GetPostByCommunity(get()) }

    single { PostsDataSourceFactory(get(USE_CASE_POST)) }

    single<PostsRepository> { PostsRepositoryImpl(get(), getProperty(PostsFragment.PROPERTY_PAGED_LIST)) }

    factory { (retryCallback: () -> Unit,
                      clickItem: (post: com.uziassantosferreira.presentation.model.Post, image: ImageView) -> Unit) ->
        PostsAdapter(retryCallback, clickItem) }

    viewModel { PostsViewModel(get()) }
}

private const val USE_CASE_POST = "useCasePost"