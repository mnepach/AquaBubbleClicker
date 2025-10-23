package com.example.aquabubbleclicker.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = AquaBlue,
    secondary = BubbleBlue,
    tertiary = RainbowPink,
    background = SkyBlue,
    surface = LightCyan,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = DeepAqua,
    onSurface = DeepAqua
)

@Composable
fun AquaBubbleTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        content = content
    )
}