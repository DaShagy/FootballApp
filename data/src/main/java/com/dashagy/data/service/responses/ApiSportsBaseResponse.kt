package com.dashagy.data.service.responses

data class ApiSportsBaseResponse<T> (
    val message: String?,
    val response: T?
)
