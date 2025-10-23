package com.example.aquabubbleclicker

import com.example.aquabubbleclicker.data.model.Bubble
import com.example.aquabubbleclicker.data.repository.GameRepository
import com.example.aquabubbleclicker.domain.usecase.GenerateBubbleUseCase
import com.example.aquabubbleclicker.presentation.viewmodel.GameViewModel
import androidx.compose.ui.graphics.Color
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GameViewModelTest {

    private lateinit var repository: GameRepository
    private lateinit var useCase: GenerateBubbleUseCase
    private lateinit var viewModel: GameViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = mockk(relaxed = true)
        useCase = mockk(relaxed = true)

        coEvery { useCase.invoke(any(), any(), any()) } returns Bubble(
            id = 1,
            x = 100f,
            y = 100f,
            size = 50f,
            color = Color.Blue,
            speed = 1f
        )

        viewModel = GameViewModel(repository, useCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `onBubbleClick increments score`() = runTest {
        val bubble = Bubble(
            id = 1,
            x = 100f,
            y = 100f,
            size = 50f,
            color = Color.Blue,
            speed = 1f
        )

        viewModel.onBubbleClick(bubble)
        testDispatcher.scheduler.advanceUntilIdle()

        coVerify { repository.incrementScore() }
    }
}