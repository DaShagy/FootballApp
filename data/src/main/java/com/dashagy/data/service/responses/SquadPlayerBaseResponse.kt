package com.dashagy.data.service.responses

import com.dashagy.domain.entities.Team

data class SquadPlayerBaseResponse(
    val team: Team,
    val players: List<SquadPlayerResponse>
)
