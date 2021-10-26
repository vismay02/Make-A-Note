package com.vismay.makeanote.utils

data class Resource<out T> private constructor(
    val status: Status,
    val data: T?,
    val message: String?
) {

    companion object {
        fun <T> success(data: T? = null): Resource<T> = Resource(Status.SUCCESS, data, null)
        fun <T> error(data: T? = null, message: String?): Resource<T> =
            Resource(Status.ERROR, data, message)

        fun <T> loading(data: T? = null): Resource<T> = Resource(Status.LOADING, data, null)
    }
}
