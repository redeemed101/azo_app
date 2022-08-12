package com.fov.sermons.ui.music

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.fov.common_ui.theme.DarkGrey
import com.fov.common_ui.theme.AzoTheme
import com.fov.common_ui.theme.ThemeHelper
import com.fov.common_ui.theme.commonPadding
import com.fov.common_ui.R

@Preview(showBackground = true)
@Composable
fun prevSongRow(){
    AzoTheme {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colors.surface)
                .height(100.dp)
                .fillMaxWidth()

        ) {
            songRow(
                "1",
                "Savage Remix (Feat Beyonce)",
                "https://www.pngkit.com/png/detail/115-1150342_user-avatar-icon-iconos-de-mujeres-a-color.png",


            )
        }
    }
}

@Composable
fun songRow(songNumber : String,
            songTitle : String, songImgUrl : String,
            height : Dp = 100.dp){
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .padding(horizontal = commonPadding),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(

            songNumber,
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.h5.copy(
                MaterialTheme.colors.onSurface,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp

            ),
        )
        Card(

            modifier = Modifier
                        .width(height/2f)
                       .height(height/2f),

            shape = RoundedCornerShape(10),

            elevation = 12.dp
        ) {
            // below line we are creating a new image.
            Image(
                painter = rememberAsyncImagePainter(
                    ImageRequest.Builder(LocalContext.current).data(data = songImgUrl)
                        .apply(block = fun ImageRequest.Builder.() {
                            crossfade(true)
                            fallback(R.drawable.image_placeholder)
                            placeholder(R.drawable.image_placeholder)
                        }).build()
                ),
                contentDescription = "",
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop,

                )

        }
        Column() {
            Text(
                songTitle,
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.h6.copy(
                    MaterialTheme.colors.onSurface,
                    fontWeight = FontWeight.Bold,
                ),
            )//text

        }
        Icon(
            painterResource(R.drawable.ic_more_vertical),
            "",
            modifier = Modifier
                //.alignByBaseline()
                //.height(10.dp)
                .padding(
                    horizontal = 4.dp,
                ),
            tint = DarkGrey

        )//icon
    }
}