package com.dashagy.data.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dashagy.data.database.entities.RoomCountry

@Dao
interface CountryDao {
    @Query("SELECT * FROM Countries WHERE code IS NOT NULL OR flag IS NOT NULL")
    suspend fun getAllCountries(): List<RoomCountry>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCountry(country: RoomCountry)
}