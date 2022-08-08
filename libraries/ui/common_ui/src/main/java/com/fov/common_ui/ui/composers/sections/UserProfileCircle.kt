package com.fov.common_ui.ui.composers.sections

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.fov.common_ui.R


@Composable
fun UserProfileCircle(
    url : String,
    size : Dp,
    padding : Dp,
    hasStory : Boolean,
    noSelfStory : Boolean = false,
    modifier: Modifier = Modifier,
    clickAction : () -> Unit){
    var circleModifier : Modifier = Modifier
        .size(size)
        .clickable {
            clickAction()
        }
        .testTag(tag = "circle");
    if(hasStory) {
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
                painter = rememberImagePainter(
                    data = url,
                    builder = {
                        crossfade(true)
                        fallback(R.drawable.avatar)
                        placeholder(R.drawable.avatar)
                    }
                ),
                contentDescription = null,
                modifier = Modifier.size(128.dp)
            )



        }

        if(noSelfStory) {
            val size2 = size * 0.4f
            Box(
                modifier = Modifier
                    .height(size2)
                    .width(size2)
                    .align(Alignment.BottomEnd)
                    .offset(y = (size2 * 0.2f), x = -(size2 * 0.02f))
            ) {
                Card(modifier = Modifier
                    .height(size2)
                    .width(size2)
                    //.size(size2)
                    .clickable {

                    }
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colors.primary,
                        shape = CircleShape
                    )
                    ,
                    shape = CircleShape,
                ){
                    val size3 = size2 * 0.2f

                    Icon(
                        painterResource(R.drawable.ic_plus),
                        "",
                        modifier = Modifier
                            .padding(size3),
                        tint = MaterialTheme.colors.primary,
                    )

                }
            }


        }

    }


}