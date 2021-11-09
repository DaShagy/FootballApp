package com.dashagy.data.service.responses

import com.dashagy.domain.entities.Country

data class LeagueBaseResponse(
    val league: LeagueResponse,
    val country: Country
)