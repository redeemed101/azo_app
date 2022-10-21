package com.fov.main.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.fov.common_ui.R
import com.fov.domain.models.shorts.Short
import com.fov.domain.models.shorts.ShortType


@Composable
fun ShortCircle(
    short: Short,
    size : Dp,
    padding : Dp,
    hasShort : Boolean,
    modifier: Modifier = Modifier,
    clickAction : () -> Unit){
    var circleModifier : Modifier = Modifier
        .size(size)
        .clickable {
            clickAction()
        }
        .testTag(tag = "circle");
    if(hasShort) {
        circleModifier = Modifier
            .size(size)
            .clickable {
                clickAction()
            }
            .border(width = 4.dp, color = MaterialTheme.colors.primary, shape = CircleShape)
            .testTag(tag = "circle");
    }


    //ShimmerAnimation(size = size, isCircle = true)
    Box(
        modifier = Modifier
            .padding(padding)
            .alpha(0.5f)
    ){

        // creating a card for creating a circle image view.
        Card(

            modifier = circleModifier,

            shape = CircleShape,

            elevation = 12.dp
        ) {
            if(short.type == ShortType.IMAGE.type) {
                Image(
                    painter = rememberAsyncImagePainter(
                        ImageRequest.Builder(LocalContext.current)
                            .data(data = short.content).apply(block = fun ImageRequest.Builder.() {
                                crossfade(true)
                                fallback(R.drawable.avatar)
                                placeholder(R.drawable.avatar)
                            }).build()
                    ),
                    contentDescription = null,
                    modifier = Modifier.size(128.dp)
                )
            }
            else if(short.type == ShortType.VIDEO.type){
                Image(
                    painter = rememberAsyncImagePainter(
                        ImageRequest.Builder(LocalContext.current)
                            .data(data = "https://picsum.photos/200").apply(block = fun ImageRequest.Builder.() {
                                crossfade(true)
                                fallback(R.drawable.avatar)
                                placeholder(R.drawable.avatar)
                            }).build()
                    ),
                    contentDescription = null,
                    modifier = Modifier.size(128.dp)
                )
            }
            else if(short.type == ShortType.TEXT.type){
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.background(Color.Black),
                ) {
                    Text(
                        text = short.content,
                        modifier = Modifier.background(Color.Black),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.h5.copy(
                            MaterialTheme.colors.surface,
                            fontWeight = FontWeight.Bold,
                            fontSize = 11.sp
                        ),
                    )
                }
            }



        }


    }


}