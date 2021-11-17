package com.dashagy.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Squad Players")
data class RoomSquadPlayer (
    @PrimaryKey
    val id: Int,
    val name: String,
    val age: Int,
    val number: Int,
    val position: String,
    val photo: String,
    val team: String
)
