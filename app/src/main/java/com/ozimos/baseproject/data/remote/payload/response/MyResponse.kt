package com.ozimos.baseproject.data.remote.payload.response

import com.google.gson.annotations.SerializedName
import com.ozimos.baseproject.domain.PicsumDomain

data class MyResponse(
    val author: String? = null,
    val id: Int? = null,
    val url: String? = null,
    @SerializedName("download_url")
    val downloadUrl : String? = null
)

fun MyResponse.toDomain(): PicsumDomain =
    PicsumDomain(
        author = author ?: "",
        id = id ?: 0,
        url = url ?: "",
        downloadUrl = downloadUrl ?: ""
    )
