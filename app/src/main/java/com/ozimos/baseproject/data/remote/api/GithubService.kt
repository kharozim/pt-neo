package com.ozimos.baseproject.data.remote.api

import com.ozimos.baseproject.data.remote.payload.response.UserItem
import com.ozimos.baseproject.data.remote.payload.response.UserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubService {

    @GET("/search/users")
    suspend fun searchUser(
        @Query("q") search: String
    ): Response<UserResponse>

    @GET("/users/{username}")
    suspend fun getDetailUser(
        @Path("username") username : String
    ) : Response<UserItem>

}