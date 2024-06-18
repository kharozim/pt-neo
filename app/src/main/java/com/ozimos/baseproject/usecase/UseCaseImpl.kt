package com.ozimos.baseproject.usecase

import com.ozimos.baseproject.data.repository.MyRepository
import com.ozimos.baseproject.domain.PicsumDomain
import javax.inject.Inject

class UseCaseImpl @Inject constructor(private val repository : MyRepository) : UseCase {

    override suspend fun getListPhoto(page: Int): Result<List<PicsumDomain>> {
        return repository.getListPhoto(page)
    }
}