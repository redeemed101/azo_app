package com.fov.common_ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = FovPurple,
    primaryVariant = FovYellow,
    secondary = DarkGrey,

    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,

    onSecondary = FovBlue,
    onBackground = FovBlue,
    onSurface = FovBlue,
)

private val LightColorPalette = lightColors(
    primary = FovBlue,
    primaryVariant = FovPurple,
    secondary = MainGrey,
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = DarkThemeBlack,
    onBackground = DarkThemeBlack,
    onSurface = DarkThemeBlack,

    /* Other default colors to override
background = Color.White,
surface = Color.White,
onPrimary = Color.White,
onSecondary = Color.Black,
onBackground = Color.Black,
onSurface = Color.Black,
*/
)

@Composable
fun AzoTheme(darkTheme: Boolean = ThemeHelper.isDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}