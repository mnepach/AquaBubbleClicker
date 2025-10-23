package com.example.aquabubbleclicker.presentation.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.aquabubbleclicker.shared.BubbleData
import com.example.aquabubbleclicker.ui.theme.*

@Composable
fun BubbleItem(
    bubble: BubbleData,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isPopping by remember { mutableStateOf(false) }

    val scale by animateFloatAsState(
        targetValue = if (isPopping) 0f else 1f,
        animationSpec = tween(200),
        finishedListener = { if (isPopping) onClick() }
    )

    val shimmer = rememberInfiniteTransition()
    val shimmerAlpha by shimmer.animateFloat(
        initialValue = 0.5f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    val colors = listOf(AquaBlue, BubbleBlue, RainbowPink, RainbowYellow, GlassWhite)
    val bubbleColor = colors[bubble.colorIndex]

    Box(
        modifier = modifier
            .offset(x = bubble.x.dp, y = bubble.y.dp)
            .size(bubble.size.dp)
            .scale(scale)
            .clip(CircleShape)
            .clickable { isPopping = true }
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(
                        bubbleColor.copy(alpha = shimmerAlpha),
                        bubbleColor.copy(alpha = 0.6f),
                        Color.White.copy(alpha = 0.3f),
                        Color.Gray.copy(alpha = 0.2f) // Shadow effect
                    ),
                    radius = bubble.size * 1.5f
                )
            )
            .alpha(shimmerAlpha)
    ) {
        // Inner highlight for 3D effect
        Box(
            modifier = Modifier
                .size(bubble.size.dp * 0.3f)
                .offset(x = (-bubble.size * 0.1f).dp, y = (-bubble.size * 0.1f).dp)
                .background(Color.White.copy(alpha = 0.5f), CircleShape)
        )
    }
}