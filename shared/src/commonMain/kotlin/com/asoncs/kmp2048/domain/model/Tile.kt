package com.asoncs.kmp2048.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Tile(
    val value: Int,
    val row: Int,
    val col: Int,
    val id: Long = nextId()
) {
    companion object {
        private var idCounter = 0L

        fun nextId(): Long = ++idCounter

        fun resetIdCounter() {
            idCounter = 0L
        }
    }
}
