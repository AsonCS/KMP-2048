package com.asoncs.kmp2048.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.asoncs.kmp2048.domain.model.Board
import com.asoncs.kmp2048.domain.model.Direction
import com.asoncs.kmp2048.ui.theme.GameColors
import kotlin.math.abs

@Composable
fun GameGrid(
    board: Board,
    onSwipe: (Direction) -> Unit,
    modifier: Modifier = Modifier
) {
    val grid = board.toGrid()
    var dragOffset by remember { mutableStateOf(Offset.Zero) }

    Box(
        modifier = modifier
            .aspectRatio(1f)
            .clip(RoundedCornerShape(8.dp))
            .background(GameColors.GridBackground)
            .padding(6.dp)
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = {
                        dragOffset = Offset.Zero
                    },
                    onDrag = { change, dragAmount ->
                        change.consume()
                        dragOffset += dragAmount
                    },
                    onDragEnd = {
                        val (dx, dy) = dragOffset
                        val minSwipeDistance = 50f

                        if (abs(dx) > minSwipeDistance || abs(dy) > minSwipeDistance) {
                            val direction = if (abs(dx) > abs(dy)) {
                                if (dx > 0) Direction.RIGHT else Direction.LEFT
                            } else {
                                if (dy > 0) Direction.DOWN else Direction.UP
                            }
                            onSwipe(direction)
                        }
                        dragOffset = Offset.Zero
                    }
                )
            }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            for (row in 0 until board.size) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    for (col in 0 until board.size) {
                        TileView(
                            value = grid[row][col],
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }
    }
}
