package com.asoncs.kmp2048.domain.engine

import com.asoncs.kmp2048.domain.model.*
import kotlin.random.Random

class GameEngine(
    private val random: Random = Random.Default
) {

    fun newGame(boardSize: Int = Board.GRID_SIZE, highScore: Int = 0): Board {
        val emptyBoard = Board(size = boardSize, highScore = highScore)
        val boardWithFirst = spawnTile(emptyBoard)
        return spawnTile(boardWithFirst)
    }

    fun move(board: Board, direction: Direction): Board {
        if (board.status == GameStatus.LOST) return board

        val grid = board.toGrid()
        val result = when (direction) {
            Direction.LEFT -> moveLeft(grid)
            Direction.RIGHT -> moveRight(grid)
            Direction.UP -> moveUp(grid)
            Direction.DOWN -> moveDown(grid)
        }

        if (!result.moved) return board

        val newScore = board.score + result.mergeScore
        val newHighScore = maxOf(board.highScore, newScore)

        val tiles = gridToTiles(result.grid)
        val hasWon = tiles.any { it.value >= 2048 }

        val newStatus = when {
            hasWon && board.status != GameStatus.WON_CONTINUING -> GameStatus.WON
            else -> board.status.let {
                if (it == GameStatus.WON) GameStatus.WON_CONTINUING else it
            }
        }

        val newBoard = Board(
            size = board.size,
            tiles = tiles,
            score = newScore,
            highScore = newHighScore,
            status = newStatus
        )

        return spawnTile(newBoard).let { boardAfterSpawn ->
            if (newStatus == GameStatus.WON) {
                boardAfterSpawn
            } else if (isGameOver(boardAfterSpawn)) {
                boardAfterSpawn.copy(status = GameStatus.LOST)
            } else {
                boardAfterSpawn
            }
        }
    }

    fun continueAfterWin(board: Board): Board {
        return if (board.status == GameStatus.WON) {
            board.copy(status = GameStatus.WON_CONTINUING)
        } else {
            board
        }
    }

    fun spawnTile(board: Board): Board {
        val emptyCells = board.getEmptyCells()
        if (emptyCells.isEmpty()) return board

        val (row, col) = emptyCells[random.nextInt(emptyCells.size)]
        val value = if (random.nextFloat() < 0.9f) 2 else 4
        val newTile = Tile(value = value, row = row, col = col)

        return board.copy(tiles = board.tiles + newTile)
    }

    fun isGameOver(board: Board): Boolean {
        if (board.getEmptyCells().isNotEmpty()) return false

        val grid = board.toGrid()
        val size = board.size

        for (row in 0 until size) {
            for (col in 0 until size) {
                val current = grid[row][col]
                if (col + 1 < size && current == grid[row][col + 1]) return false
                if (row + 1 < size && current == grid[row + 1][col]) return false
            }
        }

        return true
    }

    private fun moveLeft(grid: Array<IntArray>): MoveResult {
        var totalMergeScore = 0
        var moved = false
        val newGrid = Array(grid.size) { IntArray(grid[0].size) }

        for (row in grid.indices) {
            val line = grid[row].filter { it != 0 }
            val merged = mergeLine(line)
            totalMergeScore += merged.score
            for (col in merged.line.indices) {
                newGrid[row][col] = merged.line[col]
            }
            if (!moved) {
                moved = !grid[row].contentEquals(newGrid[row])
            }
        }

        return MoveResult(newGrid, moved, totalMergeScore)
    }

    private fun moveRight(grid: Array<IntArray>): MoveResult {
        var totalMergeScore = 0
        var moved = false
        val size = grid[0].size
        val newGrid = Array(grid.size) { IntArray(size) }

        for (row in grid.indices) {
            val line = grid[row].filter { it != 0 }.reversed()
            val merged = mergeLine(line)
            totalMergeScore += merged.score
            val mergedLine = merged.line.reversed()
            for (col in mergedLine.indices) {
                newGrid[row][size - mergedLine.size + col] = mergedLine[col]
            }
            if (!moved) {
                moved = !grid[row].contentEquals(newGrid[row])
            }
        }

        return MoveResult(newGrid, moved, totalMergeScore)
    }

    private fun moveUp(grid: Array<IntArray>): MoveResult {
        val transposed = transpose(grid)
        val result = moveLeft(transposed)
        return MoveResult(transpose(result.grid), result.moved, result.mergeScore)
    }

    private fun moveDown(grid: Array<IntArray>): MoveResult {
        val transposed = transpose(grid)
        val result = moveRight(transposed)
        return MoveResult(transpose(result.grid), result.moved, result.mergeScore)
    }

    internal fun mergeLine(line: List<Int>): MergeResult {
        val result = mutableListOf<Int>()
        var score = 0
        var i = 0

        while (i < line.size) {
            if (i + 1 < line.size && line[i] == line[i + 1]) {
                val merged = line[i] * 2
                result.add(merged)
                score += merged
                i += 2
            } else {
                result.add(line[i])
                i++
            }
        }

        return MergeResult(result, score)
    }

    private fun transpose(grid: Array<IntArray>): Array<IntArray> {
        val size = grid.size
        return Array(size) { col ->
            IntArray(size) { row ->
                grid[row][col]
            }
        }
    }

    private fun gridToTiles(grid: Array<IntArray>): List<Tile> {
        val tiles = mutableListOf<Tile>()
        for (row in grid.indices) {
            for (col in grid[row].indices) {
                if (grid[row][col] != 0) {
                    tiles.add(Tile(value = grid[row][col], row = row, col = col))
                }
            }
        }
        return tiles
    }

    data class MoveResult(
        val grid: Array<IntArray>,
        val moved: Boolean,
        val mergeScore: Int
    )

    data class MergeResult(
        val line: List<Int>,
        val score: Int
    )
}
