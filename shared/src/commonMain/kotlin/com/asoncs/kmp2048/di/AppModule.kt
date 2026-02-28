package com.asoncs.kmp2048.di

import com.asoncs.kmp2048.data.local.GameLocalDataSource
import com.asoncs.kmp2048.data.remote.GameRemoteDataSource
import com.asoncs.kmp2048.data.repository.GameRepository
import com.asoncs.kmp2048.data.repository.IGameRepository
import com.asoncs.kmp2048.domain.engine.GameEngine
import com.asoncs.kmp2048.presentation.GameViewModel
import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val appModule = module {
    single {
        GameEngine()
    }

    single {
        HttpClient {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    encodeDefaults = true
                })
            }
        }
    }

    single {
        GameRemoteDataSource(
            httpClient = get()
        )
    }

    single<IGameRepository> {
        GameRepository(
            localDataSource = get(),
            remoteDataSource = get()
        )
    }

    factory {
        GameViewModel(
            gameEngine = get(),
            repository = get()
        )
    }
}
