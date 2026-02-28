package com.asoncs.kmp2048.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class SyncRequest(
    val userId: String,
    val score: Int,
    val grid: List<List<Int>>,
    val timestamp: Long
)
