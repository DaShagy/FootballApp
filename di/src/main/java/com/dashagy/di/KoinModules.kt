package com.dashagy.di

import com.dashagy.data.mapper.*
import com.dashagy.data.repositories.CountriesRepositoryImpl
import com.dashagy.data.repositories.LeaguesRepositoryImpl
import com.dashagy.data.repositories.PlayersRepositoryImpl
import com.dashagy.data.repositories.TeamsRepositoryImpl
import com.dashagy.data.service.RequestGenerator
import com.dashagy.data.service.services.CountryService
import com.dashagy.data.service.services.LeagueService
import com.dashagy.data.service.services.PlayerService
import com.dashagy.data.service.services.TeamService
import com.dashagy.domain.repositories.CountriesRepository
import com.dashagy.domain.repositories.LeaguesRepository
import com.dashagy.domain.repositories.PlayersRepository
import com.dashagy.domain.repositories.TeamsRepository
import com.dashagy.domain.usecases.GetAllCountriesUseCase
import com.dashagy.domain.usecases.GetLeaguesByCountryUseCase
import com.dashagy.domain.usecases.GetTeamUseCases
import com.dashagy.domain.usecases.player_usecases.GetPlayerByIdUseCase
import com.dashagy.domain.usecases.player_usecases.GetPlayerByTeamUseCase
import com.dashagy.domain.usecases.player_usecases.GetSquadPlayersByTeamUseCase
import com.dashagy.domain.usecases.team_usecases.*
import org.koin.dsl.module

object KoinModules {
    val repositoriesModule = module {
        single { TeamService(get()) }
        single { PlayerService(get()) }
        single { CountryService(get()) }
        single { LeagueService(get()) }
        single<TeamsRepository> { TeamsRepositoryImpl(get(), get(), get()) }
        single<PlayersRepository> { PlayersRepositoryImpl(get(), get(), get(), get()) }
        single<CountriesRepository> { CountriesRepositoryImpl(get(), get(), get()) }
        single<LeaguesRepository> { LeaguesRepositoryImpl(get(), get(), get()) }
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
        single { GetPlayerByTeamUseCase(get()) }
        single { GetSquadPlayersByTeamUseCase(get()) }
    }

    val useCasesModule = module {
        single { GetTeamUseCases(get(), get(), get(), get(), get()) }
        single { GetAllCountriesUseCase(get()) }
        single { GetLeaguesByCountryUseCase(get()) }
    }

    val mappersModule = module {
        single { TeamMapperLocal() }
        single { TeamMapperService() }
        single { PlayerMapperLocal() }
        single { PlayerMapperService() }
        single { SquadPlayerMapperLocal() }
        single { SquadPlayerMapperService() }
        single { CountryMapperService() }
        single { CountryMapperLocal() }
        single { LeagueMapperLocal() }
        single { LeagueMapperService() }
    }

    val requestGeneratorModule = module{
        single { RequestGenerator(get()) }
    }
}