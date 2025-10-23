package com.example.aquabubbleclicker.shared

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.random.Random

data class BubbleData(
    val id: Int,
    val x: Float,
    val y: Float,
    val size: Float,
    val colorIndex: Int,
    val speed: Float
)

class GameLogic {
    private val _score = MutableStateFlow(0)
    val score: StateFlow<Int> = _score

    private val _highScore = MutableStateFlow(0)
    val highScore: StateFlow<Int> = _highScore

    fun incrementScore() {
        _score.value += 1
        if (_score.value > _highScore.value) {
            _highScore.value = _score.value
        }
    }

    fun resetScore() {
        _score.value = 0
    }

    fun generateRandomPosition(screenWidth: Float, screenHeight: Float): Pair<Float, Float> {
        val x = Random.nextFloat() * screenWidth
        val y = screenHeight + 100f
        return x to y
    }

    fun generateRandomSize(): Float {
        return Random.nextFloat() * (100f - 40f) + 40f
    }

    fun generateRandomSpeed(): Float {
        return Random.nextFloat() * (3f - 1f) + 1f
    }

    fun calculateBubbleMovement(currentY: Float, speed: Float, deltaTime: Float): Float {
        return currentY - (speed * deltaTime)
    }

    fun isOffScreen(y: Float): Boolean {
        return y < -200f
    }
}