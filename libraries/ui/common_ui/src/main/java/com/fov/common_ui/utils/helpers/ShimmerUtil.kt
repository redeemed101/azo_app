package com.example.common_ui.utils.helpers

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.common_ui.theme.FidarrAppComposeTheme
import com.example.common_ui.theme.ShimmerColorShades

@Composable
fun ShimmerCircleItem(
    brush: Brush,
    size : Dp
){
    Box(
        modifier = Modifier
            .padding(10.dp)
    ){

        // creating a card for creating a circle image view.
        Card(

            modifier  = Modifier
                .size(size),
            shape = CircleShape,
            elevation = 12.dp
        ) {
            // below line we are creating a new image.
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .size(size)
                    .background(brush = brush)
            )

        }


    }
}
@Composable
@Preview
fun ShimmerPreview(){
    FidarrAppComposeTheme() {
        ShimmerAnimation(size = 50.dp, isCircle = false)
    }
}
@Composable
fun ShimmerItem(
    brush: Brush,
    size : Dp,
    showSmallSpacer : Boolean = true
    ) {
    // Column composable comtaining spacer shaped like a rectangle,
    // set the [background]'s [brush] with the brush receiving from [ShimmerAnimation]
    // Composable which is the Animation you are gonna create.
    Column(modifier = Modifier.padding(16.dp)) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .size(size)
                .background(brush = brush)
        )
        if(showSmallSpacer)
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(30.dp)
                .padding(vertical = 8.dp)
                .background(brush = brush)
        )
    }
}


@Composable
fun ShimmerAnimation(
    size : Dp,
    isCircle : Boolean,
    showSmallSpacer: Boolean =  true
) {

    /*
    Create InfiniteTransition
    which holds child animation like [Transition]
    animations start running as soon as they enter
    the composition and do not stop unless they are removed
    */
    val transition = rememberInfiniteTransition()
    val translateAnim by transition.animateFloat(
        /*
        Specify animation positions,
        initial Values 0F means it
        starts from 0 position
        */
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(


            // Tween Animates between values over specified [durationMillis]
            tween(durationMillis = 1200, easing = FastOutSlowInEasing),
            RepeatMode.Reverse
        )
    )

    /*
    Create a gradient using the list of colors
    Use Linear Gradient for animating in any direction according to requirement
    start=specifies the position to start with in cartesian like system Offset(10f,10f) means x(10,0) , y(0,10)
    end = Animate the end position to give the shimmer effect using the transition created above
    */
    val brush = Brush.linearGradient(
        colors = ShimmerColorShades,
        start = Offset(10f, 10f),
        end = Offset(translateAnim, translateAnim)
    )
    if(isCircle)
        ShimmerCircleItem(brush = brush, size = size)
    else
        ShimmerItem(brush = brush, size = size,showSmallSpacer = showSmallSpacer)
}
