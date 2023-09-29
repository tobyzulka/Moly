package dev.byto.moly.utils

sealed class ResultWrapper<out T> {
    data class Success<out T>(val value: T) : ResultWrapper<T>()
    data class GenericError(
        val codeHttp: Int? = null,
        val codeResponse: Int? = null,
        val messageResponse: String? = null) : ResultWrapper<Nothing>()
    data object NetworkError : ResultWrapper<Nothing>()
}
