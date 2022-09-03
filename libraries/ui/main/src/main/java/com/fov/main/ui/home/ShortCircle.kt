package com.fov.main.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.fov.common_ui.R


@Composable
fun ShortCircle(
    imageUrl : String,
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

            Image(
                painter = rememberAsyncImagePainter(
                    ImageRequest.Builder(LocalContext.current)
                        .data(data = imageUrl).apply(block = fun ImageRequest.Builder.() {
                        crossfade(true)
                        fallback(R.drawable.avatar)
                        placeholder(R.drawable.avatar)
                    }).build()
                ),
                contentDescription = null,
                modifier = Modifier.size(128.dp)
            )



        }


    }


}