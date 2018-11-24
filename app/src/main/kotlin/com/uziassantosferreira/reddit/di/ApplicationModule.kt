package com.uziassantosferreira.reddit.di

import com.uziassantosferreira.data.datasource.DataSource
import com.uziassantosferreira.data.datasource.DataSourceImpl
import com.uziassantosferreira.data.repository.RepositoryImpl
import com.uziassantosferreira.domain.repository.Repository
import org.koin.dsl.module.module

val applicationModule = module {

    single<Repository> { RepositoryImpl(get()) }

    single<DataSource> { DataSourceImpl(get()) }
}