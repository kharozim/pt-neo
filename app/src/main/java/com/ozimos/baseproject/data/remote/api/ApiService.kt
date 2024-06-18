package com.ozimos.baseproject.data.remote.api

import com.ozimos.baseproject.data.remote.payload.response.MyResponse
import com.ozimos.baseproject.data.remote.payload.response.UserItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("v2/list")
    suspend fun getListPhoto(
        @Query("page") page: Int
    ): Response<List<MyResponse>>
}