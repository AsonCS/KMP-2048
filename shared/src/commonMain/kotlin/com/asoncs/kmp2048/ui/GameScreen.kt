package com.asoncs.kmp2048.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.asoncs.kmp2048.domain.model.GameStatus
import com.asoncs.kmp2048.presentation.GameViewModel
import com.asoncs.kmp2048.ui.components.ControlPanel
import com.asoncs.kmp2048.ui.components.GameGrid
import com.asoncs.kmp2048.ui.components.ScoreCard
import com.asoncs.kmp2048.ui.theme.GameColors

@Composable
fun GameScreen(
    viewModel: GameViewModel
) {
    val board by viewModel.board.collectAsState()
    val canUndo by viewModel.canUndo.collectAsState()
    var showWinDialog by remember { mutableStateOf(false) }
    var hasShownWinDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.initialize()
    }

    LaunchedEffect(board.status) {
        if (board.status == GameStatus.WON && !hasShownWinDialog) {
            showWinDialog = true
            hasShownWinDialog = true
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GameColors.Background)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        // Title and scores
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "2048",
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold,
                color = GameColors.TextDark
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                ScoreCard(label = "SCORE", score = board.score)
                ScoreCard(label = "BEST", score = board.highScore)
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Controls
        ControlPanel(
            onNewGame = {
                viewModel.newGame()
                hasShownWinDialog = false
            },
            onUndo = { viewModel.undo() },
            canUndo = canUndo
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Game grid
        GameGrid(
            board = board,
            onSwipe = { direction -> viewModel.move(direction) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Status messages
        when (board.status) {
            GameStatus.LOST -> {
                Text(
                    text = "Game Over!",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = GameColors.TextDark
                )
            }
            GameStatus.WON_CONTINUING -> {
                Text(
                    text = "Keep going!",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = GameColors.TextDark
                )
            }
            else -> {}
        }
    }

    // Win dialog
    if (showWinDialog) {
        AlertDialog(
            onDismissRequest = { showWinDialog = false },
            title = {
                Text(
                    text = "You Win!",
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Text("You reached 2048! Would you like to continue playing?")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showWinDialog = false
                        viewModel.continueAfterWin()
                    }
                ) {
                    Text("Continue")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showWinDialog = false
                        viewModel.newGame()
                        hasShownWinDialog = false
                    }
                ) {
                    Text("New Game")
                }
            }
        )
    }
}
