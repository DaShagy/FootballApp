package com.dashagy.domain.entities

data class Team(
    val id: Int,
    val name: String,
    val country: String?,
    val founded: Int?,
    val national: Boolean,
    val logo: String
)
