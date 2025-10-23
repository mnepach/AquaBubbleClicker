package com.example.aquabubbleclicker

import com.example.aquabubbleclicker.data.repository.GameRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GameRepositoryTest {

    private lateinit var repository: GameRepository

    @Before
    fun setup() {
        repository = GameRepository()
    }

    @Test
    fun `incrementScore increases score by 1`() = runTest {
        repository.incrementScore()
        assertEquals(1, repository.score.first())
    }

    @Test
    fun `resetScore sets score to 0`() = runTest {
        repository.incrementScore()
        repository.resetScore()
        assertEquals(0, repository.score.first())
    }

    @Test
    fun `generateBubble creates valid bubble`() {
        val bubble = repository.generateBubble(1, 1000f, 2000f)
        assertEquals(1, bubble.id)
        assert(bubble.size in 40f..100f)
    }
}