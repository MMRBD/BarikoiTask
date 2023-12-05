package com.mmrbd.barikoitask.utils.network


/**
 * A wrapper for network request handling failing requests
 * @param data
 * @param error
 * */
sealed class ApiResult<T>(
    val data: T? = null,
    val error: Failure? = null
) {
    class Success<T>(data: T) : ApiResult<T>(data)
    class Loading<T>() : ApiResult<T>()
    class Error<T>(throwable: Failure, data: T? = null) : ApiResult<T>(data, throwable)
}
