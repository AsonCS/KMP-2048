package com.asoncs.kmp2048.ui.theme

import androidx.compose.ui.graphics.Color

object GameColors {
    val Background = Color(0xFFFAF8EF)
    val GridBackground = Color(0xFFBBADA0)
    val EmptyCell = Color(0xFFCDC1B4)
    val TextDark = Color(0xFF776E65)
    val TextLight = Color(0xFFF9F6F2)
    val ScoreBackground = Color(0xFFBBADA0)
    val ButtonBackground = Color(0xFF8F7A66)

    fun tileColor(value: Int): Color = when (value) {
        2 -> Color(0xFFEEE4DA)
        4 -> Color(0xFFEDE0C8)
        8 -> Color(0xFFF2B179)
        16 -> Color(0xFFF59563)
        32 -> Color(0xFFF67C5F)
        64 -> Color(0xFFF65E3B)
        128 -> Color(0xFFEDCF72)
        256 -> Color(0xFFEDCC61)
        512 -> Color(0xFFEDC850)
        1024 -> Color(0xFFEDC53F)
        2048 -> Color(0xFFEDC22E)
        else -> Color(0xFF3C3A32)
    }

    fun tileTextColor(value: Int): Color = when {
        value <= 4 -> TextDark
        else -> TextLight
    }
}
