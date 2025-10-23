package com.example.aquabubbleclicker

import com.example.aquabubbleclicker.data.repository.GameRepository
import com.example.aquabubbleclicker.domain.usecase.GenerateBubbleUseCase
import com.example.aquabubbleclicker.presentation.viewmodel.GameViewModel
import io.mockk.mockk
import io.mockk.verify
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
        viewModel = GameViewModel(repository, useCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `onBubbleClick increments score`() = runTest {
        val bubble = mockk<com.example.aquabubbleclicker.data.model.Bubble>(relaxed = true)
        viewModel.onBubbleClick(bubble)
        verify { repository.incrementScore() }
    }
}