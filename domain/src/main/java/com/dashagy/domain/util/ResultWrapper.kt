package com.dashagy.domain.util

import java.lang.Exception

sealed class ResultWrapper <out T : Any> {
    class Success<out T : Any>(val data: T) : ResultWrapper<T>()
    class Error(val exception: Exception) : ResultWrapper<Nothing>()
    object Loading: ResultWrapper<Nothing>()
}
