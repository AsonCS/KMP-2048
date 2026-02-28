package com.asoncs.kmp2048.data.remote

import com.asoncs.kmp2048.data.remote.dto.LeaderboardEntry
import com.asoncs.kmp2048.data.remote.dto.SyncRequest
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

class GameRemoteDataSource(
    private val httpClient: HttpClient,
    private val baseUrl: String = ""
) {

    suspend fun syncGameState(request: SyncRequest): Boolean {
        if (baseUrl.isEmpty()) return false
        return try {
            val response = httpClient.post("$baseUrl/sync") {
                contentType(ContentType.Application.Json)
                setBody(request)
            }
            response.status == HttpStatusCode.OK
        } catch (e: Exception) {
            false
        }
    }

    suspend fun getLeaderboard(): List<LeaderboardEntry> {
        if (baseUrl.isEmpty()) return emptyList()
        return try {
            httpClient.get("$baseUrl/leaderboard").body()
        } catch (e: Exception) {
            emptyList()
        }
    }
}
