package com.dashagy.domain.entities

data class TeamStanding(
    val rank: Int,
    val team: Team,
    val points: Int,
    val goalsDiff: Int,
    val group: String,
    val form: String,
    val status: String,
    val description: String,
    val update: String
)
