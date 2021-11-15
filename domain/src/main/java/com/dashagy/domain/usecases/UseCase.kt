package com.dashagy.domain.usecases

import com.dashagy.domain.util.ResultWrapper

fun interface UseCase{
    suspend operator fun invoke(
        fromRemote: Boolean,
        vararg params: String
    ) : ResultWrapper<List<Any>>
}