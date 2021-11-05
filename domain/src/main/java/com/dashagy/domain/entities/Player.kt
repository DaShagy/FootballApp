package com.dashagy.domain.entities

data class Player(
    val id: Int,
    val name: String,
    val firstname: String,
    val lastname: String,
    val age: Int,
    val nationality: String,
    val height: String?,
    val weight: String?,
    val injured: Boolean,
    val photo: String
)
