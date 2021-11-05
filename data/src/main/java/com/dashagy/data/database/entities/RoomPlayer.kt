package com.dashagy.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Players")
data class RoomPlayer(
    @PrimaryKey
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