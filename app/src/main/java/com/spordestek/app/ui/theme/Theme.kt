package com.spordestek.app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF10B981),
    onPrimary = Color.White,
    primaryContainer = Color(0xFF34D399),
    onPrimaryContainer = Color.White,
    secondary = Color(0xFF3B82F6),
    onSecondary = Color.White,
    secondaryContainer = Color(0xFF60A5FA),
    onSecondaryContainer = Color.White,
    tertiary = Color(0xFFF59E0B),
    onTertiary = Color.White,
    background = Color.White,
    onBackground = Color(0xFF111827),
    surface = Color.White,
    onSurface = Color(0xFF111827),
    surfaceVariant = Color(0xFFF3F4F6),
    onSurfaceVariant = Color(0xFF6B7280),
    error = Color(0xFFEF4444),
    onError = Color.White
)

@Composable
fun SporDestekTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}
