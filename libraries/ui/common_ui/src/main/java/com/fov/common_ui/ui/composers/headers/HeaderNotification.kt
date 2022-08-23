package com.fov.common_ui.ui.composers.headers

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.fov.common_ui.extensions.length
import com.fov.common_ui.theme.AzoTheme
import com.fov.common_ui.R
import com.fov.common_ui.theme.ThemeHelper


@Preview(showBackground = true)
@Composable
fun prevHeaderNotifications(){
    AzoTheme {
       HeaderNotifications(
              1
        ,100.dp){

       }
    }
}
@Composable
fun HeaderNotifications(

                        notif : Int,
                        size: Dp,
                        iconColor: Color = MaterialTheme.colors.onPrimary,
                        action : () -> Unit,
){
    val numDigits = notif.length();
    var notifText = notif.toString();
    if(numDigits == 1){
        notifText = "$notif+";
    }
    BoxWithConstraints() {

        Box(
            modifier = Modifier
                .padding(20.dp)
                .clickable {
                   action()
                }
        ) {
            var notificationIcon = R.drawable.ic_notification_empty
            if(ThemeHelper.isDarkTheme()){
                notificationIcon = R.drawable.ic_notification_dark_empty
            }
            Icon(
                painterResource(notificationIcon),
                "",
                modifier = Modifier
                    .width(size)
                    .height(size),
                tint = iconColor,


            )
            if(notif > 0) {
                val size2 = size * 0.6f
                Canvas(modifier = Modifier.size(size2)
                    .align(Alignment.TopEnd)
                    .offset(y = (size2 * 0f)), onDraw = {
                    val size3 = size2.toPx()
                    drawCircle(
                        color = Color.Red,
                        radius = size3 / 2f
                    )
                })
                Text(notifText,
                    Modifier
                        .align(Alignment.TopEnd)

                        .offset(
                            y = (size2 * 0.1f),
                            x = -(size2 * 0.2f)
                        ),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.body2.copy(
                        Color.White,
                        fontSize = with(LocalDensity.current) {
                            (size2 * 0.6f).toSp()
                        }
                    )
                )
            }

        }
    }
}
@Composable

fun circleShape(size : Dp) {
    Canvas(modifier = Modifier.size(size), onDraw = {
        val size = size.toPx()
        drawCircle(
            color = Color.Red,
            radius = size / 2f
        )
    })
}