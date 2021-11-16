package com.dashagy.domain.usecases

import com.dashagy.domain.util.ResultWrapper

fun interface UseCase <out T>{
    suspend operator fun invoke(
        fromRemote: Boolean,
        vararg params: String
    ) : ResultWrapper<List<T>>
}