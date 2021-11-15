package com.dashagy.domain.usecases

import com.dashagy.domain.repositories.LeaguesRepository
import com.dashagy.domain.util.ResultWrapper

class GetLeaguesByCountryUseCase(
    private val leaguesRepository: LeaguesRepository
) : UseCase {

    override suspend operator fun invoke(
        fromRemote: Boolean,
        vararg params: String
    ): ResultWrapper<List<Any>> =
        leaguesRepository.getLeaguesByCountry(
            country = params[0],
            fromRemote = fromRemote
        )
}