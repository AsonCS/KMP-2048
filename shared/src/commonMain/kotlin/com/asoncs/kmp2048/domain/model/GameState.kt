package com.asoncs.kmp2048.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class GameState(
    val grid: List<List<Int>>,
    val score: Int,
    val highScore: Int,
    val status: GameStatus,
    val hasWonBefore: Boolean = false
) {
    companion object {
        fun fromBoard(board: Board): GameState {
            val gridData = board.toGrid().map { it.toList() }
            return GameState(
                grid = gridData,
                score = board.score,
                highScore = board.highScore,
                status = board.status,
                hasWonBefore = board.status == GameStatus.WON || board.status == GameStatus.WON_CONTINUING
            )
        }
    }
}
