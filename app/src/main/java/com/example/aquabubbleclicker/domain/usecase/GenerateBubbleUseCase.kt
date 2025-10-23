package com.example.aquabubbleclicker.domain.usecase

import com.example.aquabubbleclicker.data.model.Bubble
import com.example.aquabubbleclicker.data.repository.GameRepository
import javax.inject.Inject

class GenerateBubbleUseCase @Inject constructor(
    private val repository: GameRepository
) {
    suspend operator fun invoke(id: Int, screenWidth: Float, screenHeight: Float): Bubble {
        return repository.generateBubble(id, screenWidth, screenHeight)
    }
}