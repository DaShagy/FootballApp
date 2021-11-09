package com.dashagy.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Countries")
data class RoomCountry(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val name: String,
    val code: String?,
    val flag: String?
)
