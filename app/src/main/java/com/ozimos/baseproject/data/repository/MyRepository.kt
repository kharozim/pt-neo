package com.ozimos.baseproject.data.repository

import com.ozimos.baseproject.domain.PicsumDomain

interface MyRepository {
    suspend fun getListPhoto(page : Int): Result<List<PicsumDomain>>
}