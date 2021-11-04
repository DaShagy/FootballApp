package com.dashagy.data.service.responses

import com.dashagy.domain.entities.Player
import com.dashagy.domain.entities.PlayerStats

data class PlayerBaseResponse(
    val player: Player,
    val statistics: List<PlayerStats>
)
