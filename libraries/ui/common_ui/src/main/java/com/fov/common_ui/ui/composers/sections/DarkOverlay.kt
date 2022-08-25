package com.fov.common_ui.ui.composers.sections

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color

@Composable
fun DarkOverlay(
                backgroundColor: Color = MaterialTheme.colors.onSurface,
                alpha: Float = 0.5f,
                content: @Composable BoxScope.() -> Unit   = {}
                ,action: () -> Unit = {}){
    Box(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .alpha(alpha)
            .clickable {

                action()

            }
            .background(backgroundColor)
    ) {
        content()
    }
}