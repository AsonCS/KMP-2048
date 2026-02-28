package com.asoncs.kmp2048.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.asoncs.kmp2048.ui.theme.GameColors

@Composable
fun ControlPanel(
    onNewGame: () -> Unit,
    onUndo: () -> Unit,
    canUndo: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        GameButton(
            text = "New Game",
            onClick = onNewGame,
            modifier = Modifier.weight(1f)
        )
        GameButton(
            text = "Undo",
            onClick = onUndo,
            enabled = canUndo,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun GameButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier.height(44.dp),
        shape = RoundedCornerShape(6.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = GameColors.ButtonBackground,
            contentColor = GameColors.TextLight
        )
    ) {
        Text(
            text = text,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
