package com.asoncs.kmp2048.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.asoncs.kmp2048.ui.theme.GameColors

@Composable
fun TileView(
    value: Int,
    modifier: Modifier = Modifier
) {
    val backgroundColor = if (value == 0) GameColors.EmptyCell else GameColors.tileColor(value)
    val textColor = GameColors.tileTextColor(value)
    val fontSize = when {
        value < 100 -> 32.sp
        value < 1000 -> 24.sp
        else -> 18.sp
    }

    Box(
        modifier = modifier
            .aspectRatio(1f)
            .padding(3.dp)
            .clip(RoundedCornerShape(6.dp))
            .background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        if (value != 0) {
            Text(
                text = value.toString(),
                color = textColor,
                fontSize = fontSize,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
