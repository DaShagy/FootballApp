package com.dashagy.di

import com.dashagy.data.mapper.PlayerMapperLocal
import com.dashagy.data.mapper.PlayerMapperService
import com.dashagy.data.mapper.TeamMapperLocal
import com.dashagy.data.mapper.TeamMapperService
import com.dashagy.data.repositories.PlayersRepositoryImpl
import com.dashagy.data.repositories.TeamsRepositoryImpl
import com.dashagy.data.service.RequestGenerator
import com.dashagy.data.service.services.PlayerService
import com.dashagy.data.service.services.TeamService
import com.dashagy.domain.repositories.PlayersRepository
import com.dashagy.domain.repositories.TeamsRepository
import com.dashagy.domain.usecases.GetTeamUseCases
import com.dashagy.domain.usecases.player_usecases.GetPlayerByIdUseCase
import com.dashagy.domain.usecases.team_usecases.*
import org.koin.dsl.module

object KoinModules {
    val repositoriesModule = module {
        single { TeamService(get()) }
        single { PlayerService(get()) }
        single<TeamsRepository> { TeamsRepositoryImpl(get(), get(), get()) }
        single<PlayersRepository> { PlayersRepositoryImpl(get(), get(), get()) }
    }

    val teamUseCasesModule = module {
        single { GetTeamByIdUseCase(get()) }
        single { GetTeamByNameUseCase(get()) }
        single { GetTeamByLeagueUseCase(get()) }
        single { GetTeamByCountryUseCase(get()) }
        single { GetTeamBySearchUseCase(get()) }
    }

    val playerUseCasesModule = module {
        single { GetPlayerByIdUseCase(get()) }
    }

    val useCasesModule = module {
        single { GetTeamUseCases(get(), get(), get(), get(), get()) }
    }

    val mappersModule = module {
        single { TeamMapperLocal() }
        single { TeamMapperService() }
        single { PlayerMapperLocal() }
        single { PlayerMapperService() }
    }

    val requestGeneratorModule = module{
        single { RequestGenerator(get()) }
    }
}