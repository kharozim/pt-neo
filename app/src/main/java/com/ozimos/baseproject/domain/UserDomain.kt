package com.ozimos.baseproject.domain

data class UserDomain(
    val id: Int,
    val login: String,
    val avatarUrl: String,
    val publicRepos : Int,
    val name : String,
    val htmlUrl : String
)
