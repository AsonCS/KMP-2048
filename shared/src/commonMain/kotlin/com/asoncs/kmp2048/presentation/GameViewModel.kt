package com.asoncs.kmp2048.presentation

import com.asoncs.kmp2048.data.repository.IGameRepository
import com.asoncs.kmp2048.domain.engine.GameEngine
import com.asoncs.kmp2048.domain.model.Board
import com.asoncs.kmp2048.domain.model.Direction
import com.asoncs.kmp2048.domain.model.GameStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class GameViewModel(
    private val gameEngine: GameEngine,
    private val repository: IGameRepository
) {
    private val _board = MutableStateFlow(Board())
    val board: StateFlow<Board> = _board.asStateFlow()

    private val _undoStack = mutableListOf<Board>()

    fun initialize() {
        val saved = repository.loadGame()
        if (saved != null) {
            _board.value = saved
        } else {
            val highScore = repository.getHighScore()
            _board.value = gameEngine.newGame(highScore = highScore)
        }
    }

    fun move(direction: Direction) {
        val current = _board.value
        if (current.status == GameStatus.LOST) return
        if (current.status == GameStatus.WON) return

        _undoStack.add(current)
        if (_undoStack.size > MAX_UNDO_HISTORY) {
            _undoStack.removeAt(0)
        }

        val newBoard = gameEngine.move(current, direction)
        if (newBoard != current) {
            _board.value = newBoard
            repository.saveGame(newBoard)
        } else {
            _undoStack.removeLastOrNull()
        }
    }

    fun newGame() {
        _undoStack.clear()
        val highScore = _board.value.highScore
        val newBoard = gameEngine.newGame(highScore = highScore)
        _board.value = newBoard
        repository.clearSavedGame()
        repository.saveGame(newBoard)
    }

    fun undo() {
        val previous = _undoStack.removeLastOrNull() ?: return
        _board.value = previous
        repository.saveGame(previous)
    }

    fun continueAfterWin() {
        val current = _board.value
        if (current.status == GameStatus.WON) {
            _board.value = gameEngine.continueAfterWin(current)
            repository.saveGame(_board.value)
        }
    }

    val canUndo: Boolean
        get() = _undoStack.isNotEmpty()

    companion object {
        private const val MAX_UNDO_HISTORY = 10
    }
}
