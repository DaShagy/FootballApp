package com.dashagy.domain.entities

data class Venue(
    val id: Int,
    val name: String,
    val address: String,
    val city: String,
    val capacity: Int,
    val surface: String,
    val image: String
)