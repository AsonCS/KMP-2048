package com.asoncs.kmp2048.domain.engine

import com.asoncs.kmp2048.domain.model.Board
import com.asoncs.kmp2048.domain.model.Direction
import com.asoncs.kmp2048.domain.model.GameStatus
import com.asoncs.kmp2048.domain.model.Tile
import kotlin.random.Random
import kotlin.test.*

class GameEngineTest {

    private lateinit var engine: GameEngine

    @BeforeTest
    fun setup() {
        engine = GameEngine(random = Random(42))
    }

    // --- New Game Tests ---

    @Test
    fun newGame_shouldCreateBoardWithTwoTiles() {
        val board = engine.newGame()
        assertEquals(2, board.tiles.size)
        assertEquals(0, board.score)
        assertEquals(GameStatus.IN_PROGRESS, board.status)
    }

    @Test
    fun newGame_shouldPreserveHighScore() {
        val board = engine.newGame(highScore = 100)
        assertEquals(100, board.highScore)
    }

    @Test
    fun newGame_tilesHaveValueTwoOrFour() {
        val board = engine.newGame()
        board.tiles.forEach { tile ->
            assertTrue(tile.value == 2 || tile.value == 4)
        }
    }

    // --- Merge Line Tests ---

    @Test
    fun mergeLine_twoSameValues_shouldMerge() {
        val result = engine.mergeLine(listOf(2, 2))
        assertEquals(listOf(4), result.line)
        assertEquals(4, result.score)
    }

    @Test
    fun mergeLine_fourSameValues_shouldMergePairwise() {
        // [2, 2, 2, 2] -> [4, 4]
        val result = engine.mergeLine(listOf(2, 2, 2, 2))
        assertEquals(listOf(4, 4), result.line)
        assertEquals(8, result.score)
    }

    @Test
    fun mergeLine_noMerge_shouldKeepValues() {
        val result = engine.mergeLine(listOf(2, 4, 8))
        assertEquals(listOf(2, 4, 8), result.line)
        assertEquals(0, result.score)
    }

    @Test
    fun mergeLine_singleValue_shouldKeep() {
        val result = engine.mergeLine(listOf(4))
        assertEquals(listOf(4), result.line)
        assertEquals(0, result.score)
    }

    @Test
    fun mergeLine_emptyList_shouldReturnEmpty() {
        val result = engine.mergeLine(emptyList())
        assertEquals(emptyList(), result.line)
        assertEquals(0, result.score)
    }

    @Test
    fun mergeLine_threeSameValues_shouldMergeFirstPair() {
        // [2, 2, 2] -> [4, 2]
        val result = engine.mergeLine(listOf(2, 2, 2))
        assertEquals(listOf(4, 2), result.line)
        assertEquals(4, result.score)
    }

    // --- Move Tests ---

    @Test
    fun moveRight_basicMerge() {
        // [2, 0, 2, 0] -> should merge to [0, 0, 0, 4]
        val tiles = listOf(
            Tile(value = 2, row = 0, col = 0),
            Tile(value = 2, row = 0, col = 2)
        )
        val board = Board(tiles = tiles, score = 0)
        val result = engine.move(board, Direction.RIGHT)

        val grid = result.toGrid()
        assertEquals(4, grid[0][3])
        assertEquals(4, result.score)
    }

    @Test
    fun moveLeft_basicMerge() {
        // [0, 2, 0, 2] -> [4, 0, 0, 0]
        val tiles = listOf(
            Tile(value = 2, row = 0, col = 1),
            Tile(value = 2, row = 0, col = 3)
        )
        val board = Board(tiles = tiles, score = 0)
        val result = engine.move(board, Direction.LEFT)

        val grid = result.toGrid()
        assertEquals(4, grid[0][0])
        assertEquals(4, result.score)
    }

    @Test
    fun moveUp_basicMerge() {
        val tiles = listOf(
            Tile(value = 2, row = 0, col = 0),
            Tile(value = 2, row = 2, col = 0)
        )
        val board = Board(tiles = tiles, score = 0)
        val result = engine.move(board, Direction.UP)

        val grid = result.toGrid()
        assertEquals(4, grid[0][0])
        assertEquals(4, result.score)
    }

    @Test
    fun moveDown_basicMerge() {
        val tiles = listOf(
            Tile(value = 2, row = 0, col = 0),
            Tile(value = 2, row = 2, col = 0)
        )
        val board = Board(tiles = tiles, score = 0)
        val result = engine.move(board, Direction.DOWN)

        val grid = result.toGrid()
        assertEquals(4, grid[3][0])
        assertEquals(4, result.score)
    }

    @Test
    fun moveRight_doublesMerge() {
        // [2, 2, 2, 2] -> [0, 0, 4, 4]
        val tiles = listOf(
            Tile(value = 2, row = 0, col = 0),
            Tile(value = 2, row = 0, col = 1),
            Tile(value = 2, row = 0, col = 2),
            Tile(value = 2, row = 0, col = 3)
        )
        val board = Board(tiles = tiles, score = 0)
        val result = engine.move(board, Direction.RIGHT)

        val grid = result.toGrid()
        assertEquals(4, grid[0][2])
        assertEquals(4, grid[0][3])
        assertEquals(8, result.score)
    }

    @Test
    fun moveRight_specAcceptanceCriteria() {
        // Move Right on [2, 0, 2, 2] results in [0, 0, 2, 4]
        val tiles = listOf(
            Tile(value = 2, row = 0, col = 0),
            Tile(value = 2, row = 0, col = 2),
            Tile(value = 2, row = 0, col = 3)
        )
        val board = Board(tiles = tiles, score = 0)
        val result = engine.move(board, Direction.RIGHT)

        val grid = result.toGrid()
        assertEquals(0, grid[0][0])
        assertEquals(0, grid[0][1])
        assertEquals(2, grid[0][2])
        assertEquals(4, grid[0][3])
    }

    @Test
    fun move_noValidMove_shouldReturnSameBoard() {
        // Tile stuck in corner with no merge possible
        val tiles = listOf(
            Tile(value = 2, row = 0, col = 3),
            Tile(value = 4, row = 1, col = 3)
        )
        val board = Board(tiles = tiles, score = 0)
        val result = engine.move(board, Direction.RIGHT)

        // Board shouldn't change (no movement possible to the right)
        assertEquals(board, result)
    }

    @Test
    fun move_shouldSpawnNewTileAfterValidMove() {
        val tiles = listOf(
            Tile(value = 2, row = 0, col = 0),
            Tile(value = 2, row = 0, col = 1)
        )
        val board = Board(tiles = tiles, score = 0)
        val result = engine.move(board, Direction.LEFT)

        // After merge: 1 merged tile + 1 new spawned tile = 2
        assertEquals(2, result.tiles.size)
    }

    @Test
    fun move_shouldAccumulateScore() {
        val tiles = listOf(
            Tile(value = 2, row = 0, col = 0),
            Tile(value = 2, row = 0, col = 1)
        )
        val board = Board(tiles = tiles, score = 10)
        val result = engine.move(board, Direction.LEFT)

        assertEquals(14, result.score) // 10 + 4
    }

    @Test
    fun move_shouldUpdateHighScore() {
        val tiles = listOf(
            Tile(value = 2, row = 0, col = 0),
            Tile(value = 2, row = 0, col = 1)
        )
        val board = Board(tiles = tiles, score = 0, highScore = 2)
        val result = engine.move(board, Direction.LEFT)

        assertEquals(4, result.highScore)
    }

    @Test
    fun move_lostBoard_shouldNotMove() {
        val board = Board(status = GameStatus.LOST)
        val result = engine.move(board, Direction.LEFT)
        assertEquals(board, result)
    }

    // --- Game Over Tests ---

    @Test
    fun isGameOver_emptyBoard_shouldReturnFalse() {
        val board = Board()
        assertFalse(engine.isGameOver(board))
    }

    @Test
    fun isGameOver_fullBoardWithMerges_shouldReturnFalse() {
        val tiles = mutableListOf<Tile>()
        val values = arrayOf(
            intArrayOf(2, 4, 2, 4),
            intArrayOf(4, 2, 4, 2),
            intArrayOf(2, 4, 2, 4),
            intArrayOf(4, 2, 4, 2)
        )
        // Make two adjacent tiles same to allow merge
        values[0][0] = 2
        values[0][1] = 2

        for (row in 0 until 4) {
            for (col in 0 until 4) {
                tiles.add(Tile(value = values[row][col], row = row, col = col))
            }
        }
        val board = Board(tiles = tiles)
        assertFalse(engine.isGameOver(board))
    }

    @Test
    fun isGameOver_fullBoardNoMerges_shouldReturnTrue() {
        val tiles = mutableListOf<Tile>()
        val values = arrayOf(
            intArrayOf(2, 4, 8, 16),
            intArrayOf(32, 64, 128, 256),
            intArrayOf(512, 1024, 2, 4),
            intArrayOf(8, 16, 32, 64)
        )
        for (row in 0 until 4) {
            for (col in 0 until 4) {
                tiles.add(Tile(value = values[row][col], row = row, col = col))
            }
        }
        val board = Board(tiles = tiles)
        assertTrue(engine.isGameOver(board))
    }

    // --- Win Condition Tests ---

    @Test
    fun move_reaching2048_shouldSetWonStatus() {
        val tiles = listOf(
            Tile(value = 1024, row = 0, col = 0),
            Tile(value = 1024, row = 0, col = 1)
        )
        val board = Board(tiles = tiles, score = 0)
        val result = engine.move(board, Direction.LEFT)

        assertEquals(GameStatus.WON, result.status)
    }

    @Test
    fun continueAfterWin_shouldSetWonContinuingStatus() {
        val board = Board(status = GameStatus.WON)
        val result = engine.continueAfterWin(board)
        assertEquals(GameStatus.WON_CONTINUING, result.status)
    }

    @Test
    fun continueAfterWin_notWon_shouldNotChange() {
        val board = Board(status = GameStatus.IN_PROGRESS)
        val result = engine.continueAfterWin(board)
        assertEquals(GameStatus.IN_PROGRESS, result.status)
    }

    // --- Spawn Tests ---

    @Test
    fun spawnTile_shouldAddOneTile() {
        val board = Board()
        val result = engine.spawnTile(board)
        assertEquals(1, result.tiles.size)
    }

    @Test
    fun spawnTile_spawnedTileHasValue2Or4() {
        val board = Board()
        val result = engine.spawnTile(board)
        assertTrue(result.tiles[0].value == 2 || result.tiles[0].value == 4)
    }

    @Test
    fun spawnTile_fullBoard_shouldNotAddTile() {
        val tiles = mutableListOf<Tile>()
        for (row in 0 until 4) {
            for (col in 0 until 4) {
                tiles.add(Tile(value = 2, row = row, col = col))
            }
        }
        val board = Board(tiles = tiles)
        val result = engine.spawnTile(board)
        assertEquals(16, result.tiles.size)
    }

    @Test
    fun spawnTile_shouldSpawnInEmptyCell() {
        val tiles = listOf(
            Tile(value = 2, row = 0, col = 0)
        )
        val board = Board(tiles = tiles)
        val result = engine.spawnTile(board)

        assertEquals(2, result.tiles.size)
        val newTile = result.tiles.last()
        assertFalse(newTile.row == 0 && newTile.col == 0)
    }

    // --- Board Model Tests ---

    @Test
    fun board_toGrid_shouldProduceCorrectGrid() {
        val tiles = listOf(
            Tile(value = 2, row = 0, col = 0),
            Tile(value = 4, row = 1, col = 2),
            Tile(value = 8, row = 3, col = 3)
        )
        val board = Board(tiles = tiles)
        val grid = board.toGrid()

        assertEquals(2, grid[0][0])
        assertEquals(4, grid[1][2])
        assertEquals(8, grid[3][3])
        assertEquals(0, grid[0][1])
    }

    @Test
    fun board_getEmptyCells_shouldReturnCorrectCells() {
        val tiles = listOf(
            Tile(value = 2, row = 0, col = 0)
        )
        val board = Board(tiles = tiles)
        val emptyCells = board.getEmptyCells()

        assertEquals(15, emptyCells.size)
        assertFalse(emptyCells.contains(0 to 0))
    }

    @Test
    fun board_getTileAt_shouldReturnCorrectTile() {
        val tile = Tile(value = 4, row = 1, col = 2)
        val board = Board(tiles = listOf(tile))

        assertEquals(tile, board.getTileAt(1, 2))
        assertNull(board.getTileAt(0, 0))
    }

    // --- US1 Acceptance Criteria ---

    @Test
    fun us1_swipeRight_mergeAndScoreIncrease() {
        // Swipe Right on a row [2, 0, 2, 0] results in [0, 0, 0, 4]
        val tiles = listOf(
            Tile(value = 2, row = 0, col = 0),
            Tile(value = 2, row = 0, col = 2)
        )
        val board = Board(tiles = tiles, score = 0)
        val result = engine.move(board, Direction.RIGHT)

        val grid = result.toGrid()
        assertEquals(4, grid[0][3])
        assertEquals(4, result.score) // Score increases by 4
    }

    @Test
    fun us1_newTileSpawnsAfterValidMove() {
        val tiles = listOf(
            Tile(value = 2, row = 0, col = 0),
            Tile(value = 2, row = 0, col = 2)
        )
        val board = Board(tiles = tiles, score = 0)
        val result = engine.move(board, Direction.RIGHT)

        // After merge: 1 merged tile + 1 spawned tile = 2
        assertEquals(2, result.tiles.size)
    }
}
