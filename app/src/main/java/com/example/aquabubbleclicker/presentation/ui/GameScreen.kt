package com.example.aquabubbleclicker.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.aquabubbleclicker.R
import com.example.aquabubbleclicker.presentation.ui.components.BubbleItem
import com.example.aquabubbleclicker.presentation.viewmodel.GameViewModel
import com.example.aquabubbleclicker.ui.theme.*

@Composable
fun GameScreen(
    viewModel: GameViewModel = hiltViewModel()
) {
    val bubbles by viewModel.bubbles.collectAsState()
    val score by viewModel.score.collectAsState()
    val highScore by viewModel.highScore.collectAsState()
    val density = LocalDensity.current

    LaunchedEffect(Unit) {
        viewModel.updateBubbles()
    }

    // Modern font: Poppins
    val provider = GoogleFont.Provider(
        providerAuthority = "com.google.android.gms.fonts",
        providerPackage = "com.google.android.gms",
        certificates = R.array.com_google_android_gms_fonts_certs
    )
    val poppinsFontFamily = FontFamily(
        Font(googleFont = GoogleFont("Poppins"), fontProvider = provider, weight = FontWeight.Bold)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        LightCyan.copy(alpha = 0.7f),
                        SkyBlue.copy(alpha = 0.7f)
                    )
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
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Score and High Score
            Text(
                text = "Score: $score",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = poppinsFontFamily,
                color = DeepAqua,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "High Score: $highScore",
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = poppinsFontFamily,
                color = DeepAqua
            )
            Spacer(modifier = Modifier.height(16.dp))
            // Reset Button
            Button(
                onClick = { viewModel.repository.resetScore() },
                colors = ButtonDefaults.buttonColors(containerColor = AquaBlue)
            ) {
                Text(
                    text = "Reset Score",
                    fontFamily = poppinsFontFamily,
                    color = WhiteFoam
                )
            }
        }

        // Bubbles
        bubbles.forEach { bubble ->
            BubbleItem(
                bubble = bubble,
                onClick = { viewModel.onBubbleClick(bubble) }
            )
        }
    }
}