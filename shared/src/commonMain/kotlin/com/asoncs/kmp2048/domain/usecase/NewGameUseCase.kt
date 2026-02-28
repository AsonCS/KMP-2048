package com.asoncs.kmp2048.domain.usecase

import com.asoncs.kmp2048.domain.engine.GameEngine
import com.asoncs.kmp2048.domain.model.Board

class NewGameUseCase(
    private val gameEngine: GameEngine
) {
    operator fun invoke(highScore: Int = 0): Board {
        return gameEngine.newGame(highScore = highScore)
    }
}
