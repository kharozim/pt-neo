package com.ozimos.baseproject.usecase

import com.ozimos.baseproject.domain.UserDomain

interface GithubUseCase {
    suspend fun searchUser(name : String) : Result<List<UserDomain>>
    suspend fun getDetailUser(username: String): Result<UserDomain>
}