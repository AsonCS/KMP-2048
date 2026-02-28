package com.asoncs.kmp2048.data.repository

import com.asoncs.kmp2048.data.local.GameLocalDataSource
import com.asoncs.kmp2048.data.remote.GameRemoteDataSource
import com.asoncs.kmp2048.data.remote.dto.LeaderboardEntry
import com.asoncs.kmp2048.data.remote.dto.SyncRequest
import com.asoncs.kmp2048.domain.model.Board
import com.asoncs.kmp2048.domain.model.GameState
import com.asoncs.kmp2048.domain.model.Tile

interface IGameRepository {
    fun saveGame(board: Board)
    fun loadGame(): Board?
    fun getHighScore(): Int
    fun saveHighScore(score: Int)
    fun clearSavedGame()
    suspend fun syncToRemote(board: Board, userId: String): Boolean
    suspend fun getLeaderboard(): List<LeaderboardEntry>
}

class GameRepository(
    private val localDataSource: GameLocalDataSource,
    private val remoteDataSource: GameRemoteDataSource
) : IGameRepository {

    override fun saveGame(board: Board) {
        val gameState = GameState.fromBoard(board)
        localDataSource.saveGameState(gameState)
        if (board.highScore > localDataSource.loadHighScore()) {
            localDataSource.saveHighScore(board.highScore)
        }
    }

    override fun loadGame(): Board? {
        val gameState = localDataSource.loadGameState() ?: return null
        val tiles = mutableListOf<Tile>()
        for (row in gameState.grid.indices) {
            for (col in gameState.grid[row].indices) {
                val value = gameState.grid[row][col]
                if (value != 0) {
                    tiles.add(Tile(value = value, row = row, col = col))
                }
            }
        }
        return Board(
            size = gameState.grid.size,
            tiles = tiles,
            score = gameState.score,
            highScore = gameState.highScore,
            status = gameState.status
        )
    }

    override fun getHighScore(): Int {
        return localDataSource.loadHighScore()
    }

    override fun saveHighScore(score: Int) {
        localDataSource.saveHighScore(score)
    }

    override fun clearSavedGame() {
        localDataSource.clearGameState()
    }

    override suspend fun syncToRemote(board: Board, userId: String): Boolean {
        val request = SyncRequest(
            userId = userId,
            score = board.score,
            grid = board.toGrid().map { it.toList() },
            timestamp = currentTimeMillis()
        )
        return remoteDataSource.syncGameState(request)
    }

    override suspend fun getLeaderboard(): List<LeaderboardEntry> {
        return remoteDataSource.getLeaderboard()
    }

}

internal expect fun currentTimeMillis(): Long
