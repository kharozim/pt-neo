package com.ozimos.baseproject.data.repository

import com.ozimos.baseproject.domain.UserDomain

interface GithubRepository {
    suspend fun searchUser(name: String): Result<List<UserDomain>>
    suspend fun getDetailUser(username: String): Result<UserDomain>
}