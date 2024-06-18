package com.ozimos.baseproject.usecase

import com.ozimos.baseproject.domain.PicsumDomain

interface UseCase {
    suspend fun getListPhoto(page : Int) : Result<List<PicsumDomain>>
}