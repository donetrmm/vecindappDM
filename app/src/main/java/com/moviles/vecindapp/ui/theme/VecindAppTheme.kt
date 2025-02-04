package com.moviles.vecindapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    primary = Color(0xFF1E88E5),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFD0E4FF),
    onPrimaryContainer = Color(0xFF001D36),
    secondary = Color(0xFF096AAF),
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFB9F6CA),
    onSecondaryContainer = Color(0xFF002212),
    tertiary = Color(0xFFFF6D00),
    onTertiary = Color.White,
    tertiaryContainer = Color(0xFFFFDBC8),
    onTertiaryContainer = Color(0xFF331400),
    background = Color(0xFFFAFAFA),
    onBackground = Color(0xFF1A1C1E),
    surface = Color.White,
    onSurface = Color(0xFF1A1C1E),
)

private val DarkColors = darkColorScheme(
    primary = Color(0xFF93CCFF),
    onPrimary = Color(0xFF003258),
    primaryContainer = Color(0xFF00497D),
    onPrimaryContainer = Color(0xFFD0E4FF),
    secondary = Color(0xFF9DFAB5),
    onSecondary = Color(0xFF003919),
    secondaryContainer = Color(0xFF005229),
    onSecondaryContainer = Color(0xFFB9F6CA),
    tertiary = Color(0xFFFFB68E),
    onTertiary = Color(0xFF532200),
    tertiaryContainer = Color(0xFF763300),
    onTertiaryContainer = Color(0xFFFFDBC8),
    background = Color(0xFF1A1C1E),
    onBackground = Color(0xFFE2E2E6),
    surface = Color(0xFF1A1C1E),
    onSurface = Color(0xFFE2E2E6),
)

@Composable
fun VecindAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColors else LightColors

    MaterialTheme(
        colorScheme = colorScheme,
        typography = MaterialTheme.typography,
        shapes = MaterialTheme.shapes,
        content = content
    )
}

