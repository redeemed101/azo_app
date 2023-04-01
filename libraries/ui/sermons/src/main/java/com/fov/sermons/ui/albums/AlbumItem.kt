package com.fov.sermons.ui.albums

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.fov.common_ui.R
import com.fov.sermons.models.Album
import com.google.android.exoplayer2.util.Log

@Composable
fun AlbumItem(
    album: Album,
    imageSize : Dp = 200.dp,
    isDownloaded : Boolean = false,
    onDownloadedIconClicked : () -> Unit = {},
    onClick : () -> Unit = {}
){
    Log.d("AlbumImage",album.artwork)
    Column(
        modifier = Modifier
            .padding(10.dp)
            .background(MaterialTheme.colors.onSurface.copy(alpha = 0.01f))
            .clickable {
                onClick()
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if(isDownloaded){

            val bitmap =
                BitmapFactory.decodeFile(album.artwork)

            Image(
                bitmap = bitmap.asImageBitmap(),
                contentDescription = null,
                contentScale = ContentScale.FillHeight,
                modifier = Modifier
                    .size(imageSize)
                    .clip(MaterialTheme.shapes.medium)

            )

        }
        else {
            Image(
                painter = rememberAsyncImagePainter(
                         ImageRequest.Builder(LocalContext.current).data(data = album.artwork)
                        .apply(block = fun ImageRequest.Builder.() {
                            crossfade(true)
                            fallback(R.drawable.image_placeholder)
                            placeholder(R.drawable.image_placeholder)
                        }).build()
                ),
                contentDescription = null,
                modifier = Modifier
                    .size(imageSize)
                    .clip(MaterialTheme.shapes.medium)
            )
        }

        Column {
            Row {
                if (isDownloaded) {
                    Icon(
                        painter = painterResource(id = com.fov.sermons.R.drawable.ic_downloaded),
                        tint = MaterialTheme.colors.primary,
                        modifier = Modifier
                            .padding(8.dp)
                            .clickable {
                                onDownloadedIconClicked()
                            },
                        contentDescription = null
                    )
                }
                Text(

                    album.albumName,
                    modifier = Modifier.padding(top = 10.dp),
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.h5.copy(
                        MaterialTheme.colors.onSurface,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp

                    ),
                )
            }
            /*Text(

                "${album.artistName}",
                modifier = Modifier.padding(top = 4.dp),
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.h5.copy(
                    MaterialTheme.colors.onSurface,
                    fontSize = 12.sp

                ),
            )*/
        }
    }
}