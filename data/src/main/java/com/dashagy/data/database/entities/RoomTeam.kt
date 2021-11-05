package com.dashagy.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Teams")
data class RoomTeam(
    @PrimaryKey
    val id: Int,
    val name: String,
    val country: String,
    val founded: Int,
    val national: Boolean,
    val logo: String
)
