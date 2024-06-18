package com.ozimos.baseproject.di

import com.ozimos.baseproject.data.repository.GithubRepository
import com.ozimos.baseproject.data.repository.MyRepository
import com.ozimos.baseproject.usecase.GithubUseCase
import com.ozimos.baseproject.usecase.GithubUseCaseImpl
import com.ozimos.baseproject.usecase.UseCase
import com.ozimos.baseproject.usecase.UseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideUseCase(repo: MyRepository): UseCase =
        UseCaseImpl(repo)

    @Provides
    @Singleton
    fun provideGithubUseCase(repo : GithubRepository) : GithubUseCase =
        GithubUseCaseImpl(repo)
}