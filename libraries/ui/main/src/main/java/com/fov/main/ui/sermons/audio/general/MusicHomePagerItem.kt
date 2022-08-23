package com.fov.main.ui.sermons.audio.general

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter

@Composable
internal fun MusicHomePagerItem(
    image  : String,
    modifier: Modifier = Modifier,
) {
    Box(modifier) {
        // Our page content, displaying a random image
        Image(
            painter = rememberAsyncImagePainter(model = image),
            contentDescription = null,
            modifier = Modifier.matchParentSize()
        )

        // Displays the page index
        /*Text(
            text = page.toString(),
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
                .background(MaterialTheme.colors.surface, RoundedCornerShape(4.dp))
                .sizeIn(minWidth = 40.dp, minHeight = 40.dp)
                .padding(8.dp)
                .wrapContentSize(Alignment.Center)
        )*/
    }
}
