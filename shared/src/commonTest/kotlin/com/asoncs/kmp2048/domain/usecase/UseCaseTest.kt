package com.asoncs.kmp2048.domain.usecase

import com.asoncs.kmp2048.domain.engine.GameEngine
import com.asoncs.kmp2048.domain.model.Board
import com.asoncs.kmp2048.domain.model.Direction
import com.asoncs.kmp2048.domain.model.Tile
import kotlin.random.Random
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class UseCaseTest {

    private lateinit var engine: GameEngine
    private lateinit var moveTilesUseCase: MoveTilesUseCase
    private lateinit var newGameUseCase: NewGameUseCase

    @BeforeTest
    fun setup() {
        engine = GameEngine(random = Random(42))
        moveTilesUseCase = MoveTilesUseCase(engine)
        newGameUseCase = NewGameUseCase(engine)
    }

    @Test
    fun moveTilesUseCase_shouldDelegateToEngine() {
        val tiles = listOf(
            Tile(value = 2, row = 0, col = 0),
            Tile(value = 2, row = 0, col = 1)
        )
        val board = Board(tiles = tiles)
        val result = moveTilesUseCase(board, Direction.LEFT)

        assertEquals(4, result.score)
    }

    @Test
    fun newGameUseCase_shouldCreateNewBoard() {
        val board = newGameUseCase(highScore = 50)

        assertEquals(2, board.tiles.size)
        assertEquals(50, board.highScore)
        assertEquals(0, board.score)
    }
}
