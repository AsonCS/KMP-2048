package com.asoncs.kmp2048

import androidx.compose.ui.window.ComposeUIViewController
import com.asoncs.kmp2048.di.appModule
import com.asoncs.kmp2048.di.platformModule
import com.asoncs.kmp2048.presentation.GameViewModel
import com.asoncs.kmp2048.ui.GameScreen
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.mp.KoinPlatform

fun MainViewController() = ComposeUIViewController {
    val viewModel = KoinPlatform.getKoin().get<GameViewModel>()
    GameScreen(viewModel = viewModel)
}

fun initKoin() {
    stopKoin()
    startKoin {
        modules(platformModule, appModule)
    }
}
