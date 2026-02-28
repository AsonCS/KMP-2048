package com.asoncs.kmp2048.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Board(
    val size: Int = GRID_SIZE,
    val tiles: List<Tile> = emptyList(),
    val score: Int = 0,
    val highScore: Int = 0,
    val status: GameStatus = GameStatus.IN_PROGRESS
) {
    companion object {
        const val GRID_SIZE = 4
    }

    fun toGrid(): Array<IntArray> {
        val grid = Array(size) { IntArray(size) }
        tiles.forEach { tile ->
            if (tile.row in 0 until size && tile.col in 0 until size) {
                grid[tile.row][tile.col] = tile.value
            }
        }
        return grid
    }

    fun getEmptyCells(): List<Pair<Int, Int>> {
        val occupied = tiles.map { it.row to it.col }.toSet()
        val empty = mutableListOf<Pair<Int, Int>>()
        for (row in 0 until size) {
            for (col in 0 until size) {
                if (row to col !in occupied) {
                    empty.add(row to col)
                }
            }
        }
        return empty
    }

    fun getTileAt(row: Int, col: Int): Tile? {
        return tiles.find { it.row == row && it.col == col }
    }
}
