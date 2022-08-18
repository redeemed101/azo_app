package com.fov.common_ui.ui.composers.sections

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fov.common_ui.theme.AzoTheme


@Preview(showBackground = true)
@Composable
fun prevNotificationSingleRow() {
    AzoTheme {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colors.surface)
                .height(100.dp)
                .fillMaxWidth()

        ) {
            notificationsSingleRow(
                100.dp,
                "Lewis Msasa",
                "https://www.pngkit.com/png/detail/115-1150342_user-avatar-icon-iconos-de-mujeres-a-color.png",
                "Started following you",
                "2h"
            ) {
                Button(
                    onClick = { /*TODO*/ },
                    shape = MaterialTheme.shapes.medium,
                ) {
                    Text("Follow", color = Color.White)
                }
            }

        }
    }
}

@Composable
fun notificationsSingleRow(
    height : Dp,
    notifier: String,
    notifierImageUrl : String,
    notification : String,
    friendlyTime : String,
    actionComposable :  @Composable() () -> Unit
                           ){
    val expanded =  remember {mutableStateOf(false) }
    BoxWithConstraints() {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
                    //.height(100.dp)
            ) {



                    Row(
                        //modifier = Modifier.fillMaxWidth(0.3f)
                    ) {
                        UserProfileCircle(
                            notifierImageUrl,
                            50.dp,
                            5.dp,
                            false
                        ) {

                        }

                        Spacer(modifier = Modifier.padding(horizontal = 4.dp))
                        Column() {
                            Text(

                                notifier,
                                modifier = Modifier.padding(top = 4.dp),
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.h5.copy(
                                    MaterialTheme.colors.onSurface,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp

                                ),
                            )
                            Text(

                                notification,
                                modifier = Modifier.padding(top = 4.dp),
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.h5.copy(
                                    MaterialTheme.colors.onSurface,
                                    fontSize = 12.sp

                                ),
                            )
                            Text(

                                friendlyTime,
                                modifier = Modifier.padding(top = 4.dp),
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.h5.copy(
                                    MaterialTheme.colors.primary,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp

                                ),
                            )
                        }
                    }

                    actionComposable()
                    /*Icon(painter = painterResource(R.drawable.ic_more_horizontal),
                        tint = MaterialTheme.colors.onSurface,
                        modifier = Modifier
                            .clickable {

                                expanded.value = !expanded.value
                            }
                            .height(20.dp)
                            .padding(horizontal = 6.dp),
                        contentDescription = "")*/

                    if (expanded.value)
                        Box(
                          modifier = Modifier.clickable {
                              expanded.value = !expanded.value
                          }
                        ) {
                            PopupMenu(
                                menuItems = listOf("Lewis", "Msasa"),

                                onClickCallbacks = listOf({
                                    Log.d("Nav", "clicked ${expanded.value}")

                                }, {
                                    Log.d("Nav", "clicked ${expanded.value}")

                                }),
                                showMenu = expanded.value
                            ) {
                                expanded.value = !expanded.value
                            }
                        }


                }
    }


}