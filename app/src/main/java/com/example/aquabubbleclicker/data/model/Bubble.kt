package com.example.aquabubbleclicker.data.model

data class Bubble(
    val id: Int,
    val x: Float,
    val y: Float,
    val size: Float,
    val color: androidx.compose.ui.graphics.Color,
    val speed: Float = 1f
)