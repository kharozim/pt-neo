package com.ozimos.baseproject.usecase

import com.ozimos.baseproject.data.repository.GithubRepository
import com.ozimos.baseproject.domain.UserDomain
import javax.inject.Inject

class GithubUseCaseImpl @Inject constructor(private val repository: GithubRepository) :
    GithubUseCase {


    override suspend fun searchUser(name: String): Result<List<UserDomain>> {
        return repository.searchUser(name)
    }

    override suspend fun getDetailUser(username: String): Result<UserDomain> {
        return repository.getDetailUser(username)
    }
}