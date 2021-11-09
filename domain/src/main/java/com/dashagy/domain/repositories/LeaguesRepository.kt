package com.dashagy.domain.repositories

import com.dashagy.domain.entities.League
import com.dashagy.domain.util.ResultWrapper

interface LeaguesRepository {
    suspend fun getLeaguesByCountry(country: String, fromRemote: Boolean) : ResultWrapper<List<League>>
}
