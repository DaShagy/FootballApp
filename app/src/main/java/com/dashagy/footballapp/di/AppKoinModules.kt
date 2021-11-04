package com.dashagy.footballapp.di

import android.app.Application
import androidx.room.Room
import com.dashagy.data.database.AppDatabase
import com.dashagy.data.database.daos.TeamDao
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

        fun provideDao(dataBase: AppDatabase): TeamDao {
            return dataBase.teamDao
        }
        single { provideDataBase(androidApplication()) }
        single { provideDao(get()) }
    }


    val viewModelsModule = module {
        viewModel { TeamsViewModel(get(), get(), get()) }
    }
}

