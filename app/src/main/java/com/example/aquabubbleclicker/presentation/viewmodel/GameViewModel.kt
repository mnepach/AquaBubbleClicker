package com.example.aquabubbleclicker.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aquabubbleclicker.shared.repository.GameRepository
import com.example.aquabubbleclicker.shared.usecase.GenerateBubbleUseCase
import com.example.aquabubbleclicker.shared.BubbleData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val repository: GameRepository,
    private val generateBubbleUseCase: GenerateBubbleUseCase
) : ViewModel() {

    private val _bubbles = MutableStateFlow<List<BubbleData>>(emptyList())
    val bubbles: StateFlow<List<BubbleData>> = _bubbles.asStateFlow()

    val score: StateFlow<Int> = repository.score
    val highScore: StateFlow<Int> = repository.highScore

    private var bubbleIdCounter = 0
    private var screenWidth = 0f
    private var screenHeight = 0f
    private var isGenerating = false
    private var isUpdating = false

    fun setScreenSize(width: Float, height: Float) {
        if (screenWidth == 0f && screenHeight == 0f) {
            screenWidth = width
            screenHeight = height
            startBubbleGeneration()
        }
    }

    private fun startBubbleGeneration() {
        if (isGenerating || screenWidth == 0f) return
        isGenerating = true

        viewModelScope.launch {
            repeat(5) {
                val newBubble = generateBubbleUseCase(
                    bubbleIdCounter++,
                    screenWidth,
                    screenHeight
                )
                _bubbles.value = _bubbles.value + newBubble
                delay(200)
            }

            while (true) {
                delay(800)
                val newBubble = generateBubbleUseCase(
                    bubbleIdCounter++,
                    screenWidth,
                    screenHeight
                )
                _bubbles.value = _bubbles.value + newBubble
            }
        }
    }

    fun updateBubbles() {
        if (isUpdating) return
        isUpdating = true

        viewModelScope.launch {
            while (true) {
                delay(16) // ~60 FPS
                _bubbles.value = _bubbles.value
                    .map { bubble ->
                        val newY = gameLogic.calculateBubbleMovement(bubble.y, bubble.speed, 3f)
                        bubble.copy(y = newY)
                    }
                    .filter { !gameLogic.isOffScreen(it.y) }
            }
        }
    }

    fun onBubbleClick(bubble: BubbleData) {
        viewModelScope.launch {
            repository.incrementScore()
            _bubbles.value = _bubbles.value.filter { it.id != bubble.id }
        }
    }

    private val gameLogic = GameLogic() // Temporary for movement logic
}