package com.example.aquabubbleclicker.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import com.example.aquabubbleclicker.R

private val LightColorScheme = lightColorScheme(
    primary = AquaBlue,
    secondary = BubbleBlue,
    tertiary = RainbowPink,
    background = SkyBlue,
    surface = LightCyan,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = DeepAqua,
    onSurface = DeepAqua
)

@Composable
fun AquaBubbleTheme(content: @Composable () -> Unit) {
    val provider = GoogleFont.Provider(
        providerAuthority = "com.google.android.gms.fonts",
        providerPackage = "com.google.android.gms",
        certificates = R.array.com_google_android_gms_fonts_certs
    )
    val poppinsFontFamily = FontFamily(
        Font(googleFont = GoogleFont("Poppins"), fontProvider = provider)
    )

    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = MaterialTheme.typography.copy(
            displayLarge = MaterialTheme.typography.displayLarge.copy(fontFamily = poppinsFontFamily),
            displayMedium = MaterialTheme.typography.displayMedium.copy(fontFamily = poppinsFontFamily),
            displaySmall = MaterialTheme.typography.displaySmall.copy(fontFamily = poppinsFontFamily),
            headlineLarge = MaterialTheme.typography.headlineLarge.copy(fontFamily = poppinsFontFamily),
            headlineMedium = MaterialTheme.typography.headlineMedium.copy(fontFamily = poppinsFontFamily),
            headlineSmall = MaterialTheme.typography.headlineSmall.copy(fontFamily = poppinsFontFamily),
            titleLarge = MaterialTheme.typography.titleLarge.copy(fontFamily = poppinsFontFamily),
            titleMedium = MaterialTheme.typography.titleMedium.copy(fontFamily = poppinsFontFamily),
            titleSmall = MaterialTheme.typography.titleSmall.copy(fontFamily = poppinsFontFamily),
            bodyLarge = MaterialTheme.typography.bodyLarge.copy(fontFamily = poppinsFontFamily),
            bodyMedium = MaterialTheme.typography.bodyMedium.copy(fontFamily = poppinsFontFamily),
            bodySmall = MaterialTheme.typography.bodySmall.copy(fontFamily = poppinsFontFamily),
            labelLarge = MaterialTheme.typography.labelLarge.copy(fontFamily = poppinsFontFamily),
            labelMedium = MaterialTheme.typography.labelMedium.copy(fontFamily = poppinsFontFamily),
            labelSmall = MaterialTheme.typography.labelSmall.copy(fontFamily = poppinsFontFamily)
        ),
        content = content
    )
}