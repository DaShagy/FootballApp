package com.dashagy.domain.entities

data class League(
    val id: Int,
    val name: String,
    val country: String,
    val type: String,
    val logo: String,
    val flag: String,
    val season: Int
)
