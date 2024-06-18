package com.ozimos.baseproject.data.repository

import com.ozimos.baseproject.data.remote.api.ApiService
import com.ozimos.baseproject.data.remote.payload.response.toDomain
import com.ozimos.baseproject.domain.PicsumDomain
import javax.inject.Inject

class MyRepositoryImpl @Inject constructor(private val api: ApiService) : MyRepository {
    override suspend fun getListPhoto(page: Int): Result<List<PicsumDomain>> {

        return try {
            val response = api.getListPhoto(page)

            if (response.isSuccessful) {
                val data = response.body() ?: emptyList()
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
}