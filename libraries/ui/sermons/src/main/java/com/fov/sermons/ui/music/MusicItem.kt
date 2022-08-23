package com.fov.sermons.ui.music

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import com.fov.common_ui.R
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.fov.sermons.models.Song

@Preview(showBackground = true)
@Composable
fun prevMusicItem(){

}
@Composable
fun MusicItem(
    song : Song,
    onClick : () -> Unit = {}
){


    Column(
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .clickable {
                onClick()
            }

    ) {
        Image(
            painter = rememberAsyncImagePainter(
                ImageRequest.Builder(LocalContext.current).data(data = song.artwork).apply(
                    block = fun ImageRequest.Builder.() {
                    crossfade(true)
                    fallback(R.drawable.image_placeholder)
                    placeholder(R.drawable.image_placeholder)
                }).build()
            ),
            contentDescription = null,
            modifier = Modifier.size(200.dp)
                .clip(MaterialTheme.shapes.medium)

        )
        Text(

            song.songName,
            modifier = Modifier.padding(top = 10.dp),
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.h5.copy(
                MaterialTheme.colors.onSurface,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp

            ),
        )

    }
}