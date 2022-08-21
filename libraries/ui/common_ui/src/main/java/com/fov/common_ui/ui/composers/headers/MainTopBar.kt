package com.fov.common_ui.ui.composers.headers

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.fov.common_ui.theme.ThemeHelper
import com.fov.common_ui.theme.White009
import com.fov.common_ui.theme.commonPadding
import com.fov.common_ui.R


@Composable
fun mainTopBar(size : Dp = 80.dp,
               numNotifications : Int,
               notificationClicked : ()-> Unit,
               profileClicked : () -> Unit,
               searchClicked : () -> Unit
){

    var backgroundColor = MaterialTheme.colors.surface;
    if(ThemeHelper.isDarkTheme())
        backgroundColor = White009

     Row(
         verticalAlignment = Alignment.CenterVertically,
         horizontalArrangement = Arrangement.SpaceBetween,
         modifier = Modifier
             .fillMaxWidth()
             .height(size)
             .background(backgroundColor)
     ){

         Image( painter = painterResource(ThemeHelper.getLogoResource()),
             "logo",
              modifier = Modifier
                  .height(size * 0.4f)
                  .padding(horizontal = commonPadding)
                  .padding(vertical = 4.dp)
         )
         Row(
             horizontalArrangement = Arrangement.SpaceEvenly
         ) {
            HeaderNotifications(notif = numNotifications,size * 0.3f){
                notificationClicked()
            }
             Icon(
                 painterResource(R.drawable.ic_search_icon),
                 "",
                 tint = MaterialTheme.colors.onSecondary,
                 modifier = Modifier
                     .padding(commonPadding)
                     .height(size * 0.3f)
                     .width(size * 0.3f)
                     .clickable {
                        searchClicked()
                     }

             )
             Icon(
                 painterResource(R.drawable.ic_profile),
                 "",
                 tint = MaterialTheme.colors.onSecondary,
                 modifier = Modifier
                     .padding(20.dp)
                     .height(size * 0.3f)
                     .width(size * 0.3f)
                     .clickable {
                        profileClicked()
                     }

             )
         }
         
     }
}