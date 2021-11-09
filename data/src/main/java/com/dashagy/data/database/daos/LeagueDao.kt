package com.dashagy.data.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dashagy.data.database.entities.RoomLeague

@Dao
interface LeagueDao {

    @Query("SELECT * FROM Leagues WHERE country = :country")
    suspend fun getLeaguesByCountry(country: String) : List<RoomLeague>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLeague(league: RoomLeague)
}