package com.dashagy.footballapp.di

import android.app.Application
import androidx.room.Room
import com.dashagy.data.database.AppDatabase
import com.dashagy.data.database.daos.PlayerDao
import com.dashagy.data.database.daos.TeamDao
import com.dashagy.footballapp.viewmodels.PlayersViewModel
import com.dashagy.footballapp.viewmodels.SquadPlayersViewModel
import com.dashagy.footballapp.viewmodels.TeamsViewModel
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

        single { provideDataBase(androidApplication()) }
        single { provideTeamDao(get()) }
        single { providePlayerDao(get()) }
    }


    val viewModelsModule = module {
        viewModel { TeamsViewModel(get()) }
        viewModel { PlayersViewModel(get(), get()) }
        viewModel { SquadPlayersViewModel(get()) }
    }
}

