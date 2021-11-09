package com.dashagy.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Leagues")
data class RoomLeague(
    @PrimaryKey
    val id: Int,
    val name: String,
    val country: String?,
    val type: String,
    val logo: String
)
