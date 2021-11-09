package com.dashagy.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dashagy.data.database.daos.CountryDao
import com.dashagy.data.database.daos.PlayerDao
import com.dashagy.data.database.daos.TeamDao
import com.dashagy.data.database.entities.RoomCountry
import com.dashagy.data.database.entities.RoomPlayer
import com.dashagy.data.database.entities.RoomSquadPlayer
import com.dashagy.data.database.entities.RoomTeam

@Database(
    entities = [
        RoomTeam::class,
        RoomPlayer::class,
        RoomSquadPlayer::class,
        RoomCountry::class
               ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract val teamDao: TeamDao
    abstract val playerDao: PlayerDao
    abstract val countryDao: CountryDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}
