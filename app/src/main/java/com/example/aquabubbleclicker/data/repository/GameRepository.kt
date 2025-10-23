package com.example.aquabubbleclicker.data.repository

import com.example.aquabubbleclicker.data.model.Bubble
import com.example.aquabubbleclicker.ui.theme.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.random.Random

@Singleton
class GameRepository @Inject constructor() {

    private val _score = MutableStateFlow(0)
    val score: StateFlow<Int> = _score

    private val colors = listOf(
        AquaBlue, BubbleBlue, RainbowPink,
        RainbowYellow, GlassWhite
    )

    fun incrementScore() {
        _score.value += 1
    }

    fun generateBubble(id: Int, screenWidth: Float, screenHeight: Float): Bubble {
        return Bubble(
            id = id,
            x = Random.nextFloat() * screenWidth,
            y = screenHeight + 100f,
            size = Random.nextFloat() * 60f + 40f,
            color = colors.random(),
            speed = Random.nextFloat() * 2f + 1f
        )
    }

    fun resetScore() {
        _score.value = 0
    }
}