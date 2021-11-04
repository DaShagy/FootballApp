package com.dashagy.footballapp

import android.app.Application
import com.dashagy.di.KoinModules
import com.dashagy.footballapp.di.AppKoinModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class SampleApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {

            androidContext(this@SampleApp)

            modules(listOf(
                AppKoinModules.viewModelsModule,
                AppKoinModules.databaseModule,
                KoinModules.mappersModule,
                KoinModules.repositoriesModule,
                KoinModules.useCasesModule,
                KoinModules.teamUseCasesModule,
                KoinModules.playerUseCasesModule,
                KoinModules.requestGeneratorModule
            ))
        }
    }
}