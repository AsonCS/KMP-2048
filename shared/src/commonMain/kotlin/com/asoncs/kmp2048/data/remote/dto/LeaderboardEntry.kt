package com.asoncs.kmp2048.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class LeaderboardEntry(
    val username: String,
    val highScore: Int
)
