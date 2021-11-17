package com.dashagy.data.repositories

import com.dashagy.data.database.daos.LeagueDao
import com.dashagy.data.mapper.LeagueMapperLocal
import com.dashagy.data.service.services.LeagueService
import com.dashagy.domain.entities.League
import com.dashagy.domain.repositories.LeaguesRepository
import com.dashagy.domain.util.ResultWrapper

class LeaguesRepositoryImpl(
    private val service: LeagueService,
    private val mapper: LeagueMapperLocal,
    private val dao: LeagueDao
) : LeaguesRepository {
    override suspend fun getLeaguesByCountry(
        country: String,
        fromRemote: Boolean,
    ): ResultWrapper<List<League>> =
        if (fromRemote){
            val leaguesResult = service.getLeaguesByCountry(country)
            if (leaguesResult is ResultWrapper.Success){
                leaguesResult.data.map {
                    dao.insertLeague(
                        mapper.transformToRepository(it)
                    )
                }
            }
            leaguesResult
        } else {
            if (dao.getLeaguesByCountry(country).isEmpty()){
                ResultWrapper.Error(Exception("Database is empty"))
            } else {
                ResultWrapper.Success(
                    dao.getLeaguesByCountry(country).map {
                        mapper.transform(it)
                    }
                )
            }
        }
}