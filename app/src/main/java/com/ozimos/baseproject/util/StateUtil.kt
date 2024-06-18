package com.ozimos.baseproject.util

sealed class StateUtil<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T) : StateUtil<T>(data)
    class Loading<T>(data: T? = null) : StateUtil<T>(data)
    class Error<T>(message: String, data: T? = null) : StateUtil<T>(data, message)
}