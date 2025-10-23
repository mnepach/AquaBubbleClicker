package com.example.aquabubbleclicker.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aquabubbleclicker.data.model.Bubble
import com.example.aquabubbleclicker.data.repository.GameRepository
import com.example.aquabubbleclicker.domain.usecase.GenerateBubbleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val repository: GameRepository,
    private val generateBubbleUseCase: GenerateBubbleUseCase
) : ViewModel() {

    private val _bubbles = MutableStateFlow<List<Bubble>>(emptyList())
    val bubbles: StateFlow<List<Bubble>> = _bubbles.asStateFlow()

    val score: StateFlow<Int> = repository.score

    private var bubbleIdCounter = 0
    private var screenWidth = 0f
    private var screenHeight = 0f

    fun setScreenSize(width: Float, height: Float) {
        screenWidth = width
        screenHeight = height
        startBubbleGeneration()
    }

    private fun startBubbleGeneration() {
        viewModelScope.launch {
            while (true) {
                delay(1000)
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
        viewModelScope.launch {
            while (true) {
                delay(16) // ~60 FPS
                _bubbles.value = _bubbles.value
                    .map { it.copy(y = it.y - it.speed * 2) }
                    .filter { it.y > -200f }
            }
        }
    }

    fun onBubbleClick(bubble: Bubble) {
        viewModelScope.launch {
            repository.incrementScore()
            _bubbles.value = _bubbles.value.filter { it.id != bubble.id }
        }
    }
}