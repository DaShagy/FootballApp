package com.dashagy.data.service.responses

data class ApiSportsBaseResponse<T> (
    val paging: PaginationResponse?,
    val message: String?,
    val response: T?
)
