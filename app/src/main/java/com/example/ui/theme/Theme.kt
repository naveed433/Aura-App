package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = AuraCoral,
    onPrimary = Color.White,
    primaryContainer = AuraCoralDark,
    onPrimaryContainer = Color.White,
    secondary = AuraGold,
    onSecondary = Color.Black,
    secondaryContainer = AuraPeach,
    onSecondaryContainer = AuraText,
    tertiary = AuraMintDark,
    background = Color(0xFF12121E),
    surface = Color(0xFF1A1A2E),
    onBackground = Color(0xFFF1F1F7),
    onSurface = Color(0xFFF1F1F7),
    surfaceVariant = Color(0xFF25253D),
    onSurfaceVariant = Color(0xFFCCCCCC),
    outline = Color(0xFF3E3E58)
)

private val LightColorScheme = lightColorScheme(
    primary = AuraCoral,
    onPrimary = Color.White,
    primaryContainer = AuraCoralSoft,
    onPrimaryContainer = AuraCoralDark,
    secondary = AuraGold,
    onSecondary = AuraText,
    secondaryContainer = AuraPeach,
    onSecondaryContainer = AuraText,
    tertiary = AuraMintDark,
    background = AuraBg,
    surface = AuraSurface,
    onBackground = AuraText,
    onSurface = AuraText,
    surfaceVariant = AuraSurface2,
    onSurfaceVariant = AuraText2,
    outline = AuraDivider
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Keep Aura's signature luxury palette
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
