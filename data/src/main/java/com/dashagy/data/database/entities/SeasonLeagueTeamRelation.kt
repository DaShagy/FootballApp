package com.dashagy.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "SeasonLeagueTeams",
    primaryKeys = ["teamId", "leagueId", "season"]
)
data class SeasonLeagueTeamRelation(
    val teamId: Int,
    val leagueId: Int,
    val season: Int
)
