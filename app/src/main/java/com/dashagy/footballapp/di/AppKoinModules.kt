package com.dashagy.footballapp.di

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.room.Room
import com.dashagy.data.database.AppDatabase
import com.dashagy.data.database.daos.CountryDao
import com.dashagy.data.database.daos.LeagueDao
import com.dashagy.data.database.daos.PlayerDao
import com.dashagy.data.database.daos.TeamDao
import com.dashagy.footballapp.viewmodels.*
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object AppKoinModules {

    val databaseModule = module {
        fun provideDataBase(application: Application): AppDatabase {
            return Room.databaseBuilder(application, AppDatabase::class.java, "APP_DATABASE")
                .fallbackToDestructiveMigration()
                .build()
        }

        fun provideTeamDao(dataBase: AppDatabase): TeamDao = dataBase.teamDao
        fun providePlayerDao(database: AppDatabase): PlayerDao = database.playerDao
        fun provideCountryDao(database: AppDatabase): CountryDao = database.countryDao
        fun provideLeagueDao(database: AppDatabase): LeagueDao = database.leagueDao

        single { provideDataBase(androidApplication()) }
        single { provideTeamDao(get()) }
        single { providePlayerDao(get()) }
        single { provideCountryDao(get()) }
        single { provideLeagueDao(get()) }
    }


    val viewModelsModule = module {
        viewModel { MainViewModel(get(), get(), get(), get()) }
    }
}

