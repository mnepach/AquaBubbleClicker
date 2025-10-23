package com.example.aquabubbleclicker.shared

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlin.test.*

/**
 * Multiplatform тесты для общей логики
 * Эти тесты работают на всех платформах (Android, iOS, JVM)
 */
class GameLogicTest {

    private lateinit var gameLogic: GameLogic

    @BeforeTest
    fun setup() {
        gameLogic = GameLogic()
    }

    @Test
    fun initialScoreIsZero() = runTest {
        assertEquals(0, gameLogic.score.first())
    }

    @Test
    fun incrementScoreIncreasesBy1() = runTest {
        gameLogic.incrementScore()
        assertEquals(1, gameLogic.score.first())
    }

    @Test
    fun multipleIncrementsWorkCorrectly() = runTest {
        repeat(15) {
            gameLogic.incrementScore()
        }
        assertEquals(15, gameLogic.score.first())
    }

    @Test
    fun resetScoreSetsTo0() = runTest {
        gameLogic.incrementScore()
        gameLogic.incrementScore()
        gameLogic.resetScore()
        assertEquals(0, gameLogic.score.first())
    }

    @Test
    fun highScoreTracksMaximum() = runTest {
        repeat(10) { gameLogic.incrementScore() }
        assertEquals(10, gameLogic.highScore.first())

        gameLogic.resetScore()
        repeat(5) { gameLogic.incrementScore() }
        assertEquals(10, gameLogic.highScore.first())

        repeat(6) { gameLogic.incrementScore() }
        assertEquals(11, gameLogic.highScore.first())
    }

    @Test
    fun generateRandomPositionReturnsValidCoordinates() {
        val (x, y) = gameLogic.generateRandomPosition(1000f, 2000f)
        assertTrue(x >= 0f && x <= 1000f, "X должен быть в диапазоне 0-1000")
        assertEquals(2100f, y, "Y должен быть screenHeight + 100")
    }

    @Test
    fun generateRandomSizeReturnsValidSize() {
        repeat(50) {
            val size = gameLogic.generateRandomSize()
            assertTrue(size >= 40f && size <= 100f, "Size должен быть 40-100")
        }
    }

    @Test
    fun generateRandomSpeedReturnsValidSpeed() {
        repeat(50) {
            val speed = gameLogic.generateRandomSpeed()
            assertTrue(speed >= 1f && speed <= 3f, "Speed должен быть 1-3")
        }
    }

    @Test
    fun calculateBubbleMovementMovesUpward() {
        val initialY = 1000f
        val speed = 2f
        val newY = gameLogic.calculateBubbleMovement(initialY, speed, deltaTime = 3f)

        assertTrue(newY < initialY, "Пузырек должен двигаться вверх")
        assertEquals(994f, newY, 0.1f)
    }

    @Test
    fun calculateBubbleMovementWithDifferentSpeeds() {
        val y = 1000f

        val slow = gameLogic.calculateBubbleMovement(y, 1f, 3f)
        val fast = gameLogic.calculateBubbleMovement(y, 2f, 3f)

        assertTrue(fast < slow, "Быстрые пузырьки движутся дальше")
    }

    @Test
    fun isOffScreenDetectsCorrectly() {
        assertTrue(gameLogic.isOffScreen(-300f), "-300 должен быть off-screen")
        assertTrue(gameLogic.isOffScreen(-201f), "-201 должен быть off-screen")
        assertFalse(gameLogic.isOffScreen(-199f), "-199 должен быть on-screen")
        assertFalse(gameLogic.isOffScreen(0f), "0 должен быть on-screen")
        assertFalse(gameLogic.isOffScreen(1000f), "1000 должен быть on-screen")
    }

    @Test
    fun bubbleDataCreation() {
        val bubble = BubbleData(
            id = 42,
            x = 100f,
            y = 200f,
            size = 50f,
            colorIndex = 2,
            speed = 1.5f
        )

        assertEquals(42, bubble.id)
        assertEquals(100f, bubble.x)
        assertEquals(200f, bubble.y)
        assertEquals(50f, bubble.size)
        assertEquals(2, bubble.colorIndex)
        assertEquals(1.5f, bubble.speed)
    }
}