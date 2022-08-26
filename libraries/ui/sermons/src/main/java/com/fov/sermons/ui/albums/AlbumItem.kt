package com.fov.sermons.ui.albums

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.LocalImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.fov.common_ui.R
import com.fov.common_ui.theme.ThemeHelper
import com.fov.sermons.models.Album

@Composable
fun AlbumItem(
    album: Album,
    isDownloaded : Boolean = false,
    onDownloadedIconClicked : () -> Unit = {},
    onClick : () -> Unit = {}
){

    Column(
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .clickable {
                onClick()
            }
    ) {
        if(isDownloaded){

            val bitmap =
                BitmapFactory.decodeFile(album.artwork)

            Image(
                bitmap = bitmap.asImageBitmap(),
                contentDescription = null,
                contentScale = ContentScale.FillHeight,
                modifier = Modifier.size(200.dp)
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
                modifier = Modifier.size(200.dp)
                    .clip(MaterialTheme.shapes.medium)
            )
        }

        Row {
            if(isDownloaded){
                Icon(
                    painter = painterResource(id = com.fov.sermons.R.drawable.ic_downloaded),
                    tint = MaterialTheme.colors.primary,
                    modifier = Modifier.padding(8.dp).clickable {
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
        Text(

            "${album.artistName}",
            modifier = Modifier.padding(top = 10.dp),
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.h5.copy(
                MaterialTheme.colors.onSurface,
                fontSize = 12.sp

            ),
        )
    }
}