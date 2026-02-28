package com.asoncs.kmp2048

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.asoncs.kmp2048.presentation.GameViewModel
import com.asoncs.kmp2048.ui.GameScreen
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {
    private val viewModel: GameViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GameScreen(viewModel = viewModel)
        }
    }
}
