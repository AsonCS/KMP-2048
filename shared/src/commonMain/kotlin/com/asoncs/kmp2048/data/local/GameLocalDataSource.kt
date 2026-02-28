package com.asoncs.kmp2048.data.local

import com.asoncs.kmp2048.domain.model.GameState
import com.russhwolf.settings.Settings
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class GameLocalDataSource(
    private val settings: Settings
) {
    private val json = Json {
        ignoreUnknownKeys = true
        encodeDefaults = true
    }

    fun saveGameState(gameState: GameState) {
        val encoded = json.encodeToString(gameState)
        settings.putString(KEY_GAME_STATE, encoded)
    }

    fun loadGameState(): GameState? {
        val encoded = settings.getStringOrNull(KEY_GAME_STATE) ?: return null
        return try {
            json.decodeFromString<GameState>(encoded)
        } catch (e: Exception) {
            null
        }
    }

    fun saveHighScore(score: Int) {
        settings.putInt(KEY_HIGH_SCORE, score)
    }

    fun loadHighScore(): Int {
        return settings.getInt(KEY_HIGH_SCORE, 0)
    }

    fun clearGameState() {
        settings.remove(KEY_GAME_STATE)
    }

    companion object {
        private const val KEY_GAME_STATE = "game_state"
        private const val KEY_HIGH_SCORE = "high_score"
    }
}
