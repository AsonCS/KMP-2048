package com.asoncs.kmp2048

import androidx.compose.runtime.Composable
import com.asoncs.kmp2048.presentation.GameViewModel
import com.asoncs.kmp2048.ui.GameScreen

@Composable
fun App(viewModel: GameViewModel) {
    GameScreen(viewModel = viewModel)
}
