package com.example.aquabubbleclicker.presentation.ui

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.aquabubbleclicker.presentation.ui.components.BubbleItem
import com.example.aquabubbleclicker.presentation.viewmodel.GameViewModel
import com.example.aquabubbleclicker.ui.theme.*

@Composable
fun GameScreen(
    viewModel: GameViewModel = hiltViewModel()
) {
    val bubbles by viewModel.bubbles.collectAsState()
    val score by viewModel.score.collectAsState()
    val density = LocalDensity.current

    val infiniteTransition = rememberInfiniteTransition()
    val gradientOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    LaunchedEffect(Unit) {
        viewModel.updateBubbles()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        LightCyan,
                        SkyBlue,
                        AquaBlue
                    ),
                    startY = gradientOffset,
                    endY = gradientOffset + 1000f
                )
            )
            .onGloballyPositioned { coordinates ->
                with(density) {
                    viewModel.setScreenSize(
                        coordinates.size.width.toFloat(),
                        coordinates.size.height.toFloat()
                    )
                }
            }
    ) {
        // Score
        Text(
            text = "Score: $score",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = WhiteFoam,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 48.dp)
        )

        // Bubbles
        bubbles.forEach { bubble ->
            BubbleItem(
                bubble = bubble,
                onClick = { viewModel.onBubbleClick(bubble) }
            )
        }
    }
}