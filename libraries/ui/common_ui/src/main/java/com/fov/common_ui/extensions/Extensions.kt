package com.fov.common_ui.extensions

import android.content.Context
import android.content.res.Resources.getSystem
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.grid.LazyGridItemScope
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.graphics.ColorUtils
import androidx.paging.compose.LazyPagingItems

val Float.toDp: Float get() = (this / getSystem().displayMetrics.density).toFloat()

val Float.toPx: Float get() = (this * getSystem().displayMetrics.density).toFloat()

val Color.isDark : Boolean get() =
    ColorUtils.calculateLuminance(this.toArgb()) < 0.5

fun Int.length() = when(this) {
    0 -> 1
    else -> Math.log10(Math.abs(toDouble())).toInt() + 1
}

fun Context.isDarkThemeOn(): Boolean {
    return true//resources.configuration.uiMode and
    //Configuration.UI_MODE_NIGHT_MASK == UI_MODE_NIGHT_YES
}

public fun <T : Any> LazyListScope.itemsCustomized(
    items: LazyPagingItems<T>,
    itemContent: @Composable LazyItemScope.(value: T?, index: Int) -> Unit
) {
    items(
        count = items.itemCount,

        ) { index ->
        itemContent(items[index], index)
    }
}

public fun <T: Any> LazyGridScope.items(
    lazyPagingItems: LazyPagingItems<T>,
    itemContent: @Composable LazyGridItemScope.(value: T?) -> Unit
) {
    items(lazyPagingItems.itemCount) { index ->
        itemContent(lazyPagingItems[index])
    }
}



@RequiresApi(Build.VERSION_CODES.O)
fun Modifier.drawColoredShadow(
    color: Color,
    alpha: Float = 0.2f,
    borderRadius: Dp = 0.dp,
    shadowRadius: Dp = 20.dp,
    offsetY: Dp = 0.dp,
    offsetX: Dp = 0.dp
) = this.drawBehind {
    val transparentColor = android.graphics.Color.toArgb(color.copy(alpha = 0.0f).value.toLong())
    val shadowColor = android.graphics.Color.toArgb(color.copy(alpha = alpha).value.toLong())
    this.drawIntoCanvas {
        val paint = Paint()
        val frameworkPaint = paint.asFrameworkPaint()
        frameworkPaint.color = transparentColor
        frameworkPaint.setShadowLayer(
            shadowRadius.toPx(),
            offsetX.toPx(),
            offsetY.toPx(),
            shadowColor
        )
        it.drawRoundRect(
            0f,
            0f,
            this.size.width,
            this.size.height,
            borderRadius.toPx(),
            borderRadius.toPx(),
            paint
        )
    }
}