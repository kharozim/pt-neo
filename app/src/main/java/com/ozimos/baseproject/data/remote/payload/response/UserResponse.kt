package com.ozimos.baseproject.data.remote.payload.response

import com.google.gson.annotations.SerializedName
import com.ozimos.baseproject.domain.UserDomain

data class UserResponse(
    val items: List<UserItem>
)

data class UserItem(
    val id: Int? = null,
    val login: String? = null,
    @field:SerializedName("public_repos")
    val publicRepos: Int? = null,
    val followers: Int? = null,
    val following: Int? = null,
    @field:SerializedName("avatar_url")
    val avatarUrl: String? = null,
    @field:SerializedName("html_url")
    val htmlUrl: String? = null,
    val company: String? = null,
    val location: String? = null,
    val name: String? = null,
)

fun UserItem.toDomain(): UserDomain = UserDomain(
    id = id ?: 0,
    login = login ?: "",
    avatarUrl = avatarUrl ?: "",
    publicRepos = publicRepos ?: 0,
    name = name ?: "",
    htmlUrl = htmlUrl ?: ""
)