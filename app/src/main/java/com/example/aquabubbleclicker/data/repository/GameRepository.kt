package com.example.aquabubbleclicker.data.repository

import com.example.aquabubbleclicker.data.model.Bubble
import com.example.aquabubbleclicker.shared.GameLogic
import com.example.aquabubbleclicker.ui.theme.*
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GameRepository @Inject constructor() {

    private val gameLogic = GameLogic()

    val score: StateFlow<Int> = gameLogic.score
    val highScore: StateFlow<Int> = gameLogic.highScore

    private val colors = listOf(
        AquaBlue, BubbleBlue, RainbowPink,
        RainbowYellow, GlassWhite
    )

    suspend fun incrementScore() = withContext(Dispatchers.Default) {
        gameLogic.incrementScore()
    }

    suspend fun generateBubble(id: Int, screenWidth: Float, screenHeight: Float): Bubble =
        withContext(Dispatchers.Default) {
            val (x, y) = gameLogic.generateRandomPosition(screenWidth, screenHeight)
            val size = gameLogic.generateRandomSize()
            val speed = gameLogic.generateRandomSpeed()

            Bubble(
                id = id,
                x = x,
                y = y,
                size = size,
                color = colors.random(),
                speed = speed
            )
        }

    fun generateBubbleSync(id: Int, screenWidth: Float, screenHeight: Float): Bubble {
        val (x, y) = gameLogic.generateRandomPosition(screenWidth, screenHeight)
        val size = gameLogic.generateRandomSize()
        val speed = gameLogic.generateRandomSpeed()

        return Bubble(
            id = id,
            x = x,
            y = y,
            size = size,
            color = colors.random(),
            speed = speed
        )
    }

    fun resetScore() {
        gameLogic.resetScore()
    }
}