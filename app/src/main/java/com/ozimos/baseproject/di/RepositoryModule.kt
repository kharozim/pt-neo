package com.ozimos.baseproject.di

import com.ozimos.baseproject.data.remote.api.ApiService
import com.ozimos.baseproject.data.remote.api.GithubService
import com.ozimos.baseproject.data.repository.GithubRepository
import com.ozimos.baseproject.data.repository.GithubRepositoryImpl
import com.ozimos.baseproject.data.repository.MyRepository
import com.ozimos.baseproject.data.repository.MyRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideMyRepository(api: ApiService): MyRepository =
        MyRepositoryImpl(api)

    @Provides
    @Singleton
    fun provideGithubRepository(api: GithubService): GithubRepository =
        GithubRepositoryImpl(api)
}