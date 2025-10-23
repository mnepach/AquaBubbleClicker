package com.example.aquabubbleclicker.presentation.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.aquabubbleclicker.data.model.Bubble

@Composable
fun BubbleItem(
    bubble: Bubble,
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
        initialValue = 0.3f,
        targetValue = 0.8f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Box(
        modifier = modifier
            .offset(x = bubble.x.dp, y = bubble.y.dp)
            .size(bubble.size.dp)
            .scale(scale)
            .clickable {
                isPopping = true
            }
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(
                        bubble.color.copy(alpha = shimmerAlpha),
                        bubble.color.copy(alpha = 0.3f),
                        Color.White.copy(alpha = 0.1f)
                    )
                ),
                shape = CircleShape
            )
    )
}