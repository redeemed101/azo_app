package com.fov.main.ui.sermons.audio.general

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.fov.common_ui.theme.commonPadding
import com.fov.sermons.R
import com.fov.sermons.models.Song

@Composable
fun SongListItem(
    songNumber :  Int? = null,
    song : Song,
    isDownloadedSong : Boolean = false,
    onDownloadedIconClicked : () -> Unit = {},
    showArtwork : Boolean = false,
    action  : () -> Unit
){
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = commonPadding, horizontal = commonPadding)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            if(isDownloadedSong){
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_down_circle),
                    tint = MaterialTheme.colors.primary,
                    modifier = Modifier.padding(8.dp).clickable {
                        onDownloadedIconClicked()
                    },
                    contentDescription = null
                )
            }
            else{
                if(songNumber != null) {
                    Text(
                        "$songNumber",
                        textAlign = TextAlign.Start,
                        style = MaterialTheme.typography.h5.copy(
                            MaterialTheme.colors.onSurface,
                            fontWeight = FontWeight.Bold
                        ),
                    )
                }
            }
            if(showArtwork){
                if(isDownloadedSong){
                    val bitmap =
                        BitmapFactory.decodeFile(song.artwork)

                    Image(
                        bitmap = bitmap.asImageBitmap(),
                        contentDescription = null,
                        contentScale = ContentScale.FillHeight,
                        modifier = Modifier.height(100.dp)

                    )
                }
                else {
                    Image(
                        painter = rememberAsyncImagePainter(
                            ImageRequest.Builder(LocalContext.current).data(data = song.artwork)
                                .apply(block = fun ImageRequest.Builder.() {
                                    crossfade(true)
                                    fallback(com.fov.common_ui.R.drawable.image_placeholder)
                                    placeholder(com.fov.common_ui.R.drawable.image_placeholder)
                                }).build()
                        ),
                        contentDescription = null,
                        //modifier = Modifier.size(200.dp)
                    )
                }
            }
            Column(
                modifier = Modifier.padding(horizontal = 10.dp)
            ) {
                Text(
                    song.songName,
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.h5.copy(
                        MaterialTheme.colors.onSurface,
                        fontWeight = FontWeight.Bold
                    ),
                )
                Text(
                    song.artistName,
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.caption.copy(
                        MaterialTheme.colors.onSurface,

                        ),

                    )
            }
        }

        Icon(
            painter = painterResource(com.fov.common_ui.R.drawable.ic_more_vertical),
            modifier = Modifier
                .clickable {
                    action()
                },
            tint = MaterialTheme.colors.onSurface,
            contentDescription = ""
        )

    }
}