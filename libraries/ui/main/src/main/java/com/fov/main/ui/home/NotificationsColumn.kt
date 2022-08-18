package com.fov.main.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fov.authentication.models.Notification
import com.fov.common_ui.theme.AzoTheme
import com.fov.common_ui.theme.commonPadding
import com.fov.common_ui.ui.composers.sections.notificationsSingleRow
import com.fov.domain.utils.constants.NotificationType


@Preview(showBackground = true)
@Composable
fun prevNotificationsColumn() {
    AzoTheme {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colors.surface)
                .fillMaxHeight()
                .fillMaxWidth()

        ) {

            val notifications = listOf(
                Notification(
                    "Lewis Msasa",
                    "12345",
                    "https://www.pngkit.com/png/detail/115-1150342_user-avatar-icon-iconos-de-mujeres-a-color.png",
                    "Started following you",
                    "2h",
                    NotificationType.FOLLOW.type
                ),
                Notification(
                    "Lewis Msasa Jnr",
                    "123",
                    "https://www.pngkit.com/png/detail/115-1150342_user-avatar-icon-iconos-de-mujeres-a-color.png",
                    "Started following you",
                    "2h",
                   NotificationType.FOLLOW.type
                )
            );
            notificationsColumn(notifications, "2 Hours ago");
        }
    }
}
@Composable
fun notificationsColumn(notifications : List<Notification>, friendlyTime : String){
      Column(
          modifier = Modifier.padding(horizontal = commonPadding)
      ) {
          Text(
              friendlyTime,
              textAlign = TextAlign.Start,
              style = MaterialTheme.typography.h5.copy(
                  MaterialTheme.colors.onSurface,
                  fontWeight = FontWeight.Bold,
              ),
          )
          Spacer(modifier = Modifier.height(20.dp))
          notifications.forEach{ notification ->
              notificationsSingleRow(
                  100.dp,
                  notification.notifier,
                  notification.notifierImgUrl,
                  notification.notification,
                  notification.friendlyTime
              ){
                  Button(
                      onClick = { /*TODO*/ },
                      shape = RoundedCornerShape(10),
                  ) {
                      Text("Follow", color = Color.White)
                  }
              }
          }


      }
}