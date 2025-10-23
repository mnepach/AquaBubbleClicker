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

/**
 * ViewModel следует MVVM архитектуре
 * Использует Kotlin Coroutines для асинхронности
 *
 * Reference: https://habr.com/ru/articles/721084/ (Part 4: MVVM)
 * Reference: https://nuancesprog.ru/p/21091/ (Coroutines)
 */
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
            // Сначала генерируем несколько пузырьков для начального заполнения
            repeat(5) {
                val newBubble = generateBubbleUseCase(
                    bubbleIdCounter++,
                    screenWidth,
                    screenHeight
                )
                _bubbles.value = _bubbles.value + newBubble
                delay(200)
            }

            // Затем продолжаем генерацию
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
                    .map { it.copy(y = it.y - it.speed * 3) }
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