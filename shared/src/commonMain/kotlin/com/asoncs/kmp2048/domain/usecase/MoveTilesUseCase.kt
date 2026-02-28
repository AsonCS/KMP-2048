package com.asoncs.kmp2048.domain.usecase

import com.asoncs.kmp2048.domain.engine.GameEngine
import com.asoncs.kmp2048.domain.model.Board
import com.asoncs.kmp2048.domain.model.Direction

class MoveTilesUseCase(
    private val gameEngine: GameEngine
) {
    operator fun invoke(board: Board, direction: Direction): Board {
        return gameEngine.move(board, direction)
    }
}
