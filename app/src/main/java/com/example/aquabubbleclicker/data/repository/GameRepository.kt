package com.example.aquabubbleclicker.data.repository

import com.example.aquabubbleclicker.data.model.Bubble
import com.example.aquabubbleclicker.ui.theme.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.random.Random

/**
 * Repository pattern implementation for game data management
 * Uses Kotlin Coroutines for async operations
 *
 * Reference: https://habr.com/ru/articles/721084/ (Part 1: Repository Pattern)
 * Reference: https://nuancesprog.ru/p/21091/ (Kotlin Coroutines)
 */
@Singleton
class GameRepository @Inject constructor() {

    private val _score = MutableStateFlow(0)
    val score: StateFlow<Int> = _score.asStateFlow()

    private val colors = listOf(
        AquaBlue, BubbleBlue, RainbowPink,
        RainbowYellow, GlassWhite
    )

    /**
     * Increments the score using coroutine context
     * Thread-safe operation with StateFlow
     */
    suspend fun incrementScore() = withContext(Dispatchers.Default) {
        _score.value += 1
    }

    /**
     * Non-suspending version for compatibility
     */
    fun incrementScoreSync() {
        _score.value += 1
    }

    /**
     * Generates a new bubble with random properties
     * Uses coroutine context for thread safety
     */
    suspend fun generateBubble(id: Int, screenWidth: Float, screenHeight: Float): Bubble =
        withContext(Dispatchers.Default) {
            Bubble(
                id = id,
                x = Random.nextFloat() * screenWidth,
                y = screenHeight + 100f,
                size = Random.nextFloat() * 60f + 40f,
                color = colors.random(),
                speed = Random.nextFloat() * 2f + 1f
            )
        }

    /**
     * Synchronous bubble generation for non-coroutine contexts
     */
    fun generateBubbleSync(id: Int, screenWidth: Float, screenHeight: Float): Bubble {
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