package com.ozimos.baseproject.data.repository

import com.ozimos.baseproject.data.remote.api.GithubService
import com.ozimos.baseproject.data.remote.payload.response.UserItem
import com.ozimos.baseproject.data.remote.payload.response.toDomain
import com.ozimos.baseproject.domain.UserDomain
import javax.inject.Inject

class GithubRepositoryImpl @Inject constructor(private val api: GithubService) : GithubRepository {
    override suspend fun searchUser(name: String): Result<List<UserDomain>> {
        return try {
            val response = api.searchUser(name)

            if (response.isSuccessful) {
                val data = response.body()?.items ?: emptyList()
                val domain = data.asSequence().map { it.toDomain() }.toList()
                Result.success(domain)
            } else {
                val responseError = response.errorBody()?.string()
                Result.failure(exception = Exception(responseError))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getDetailUser(username: String): Result<UserDomain> {
        return try {
            val response = api.getDetailUser(username)

            if (response.isSuccessful) {
                val data = response.body() ?: UserItem()
                val domain = data.toDomain()
                Result.success(domain)
            } else {
                val responseError = response.errorBody()?.string()
                Result.failure(exception = Exception(responseError))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}