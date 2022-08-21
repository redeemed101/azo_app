package com.fov.authentication.models

import com.fov.core.utils.Utils
import com.fov.domain.utils.constants.NotificationType

data class Notification(
    val notifier : String,
    val notifierId : String,
    val notifierImgUrl : String,
    val notification : String,
    val friendlyTime : String,
    val notificationType : String,
){
    object ModelMapper {
        fun from(notification: com.fov.domain.models.users.notifications.Notification) =
            Notification(
                notifier =  notification.notifier.fullName,
                notifierId = notification.notifier.id,
                notifierImgUrl = "",
                notification = notification.description,
                friendlyTime = Utils.getPrettyTime(notification.dateCreated),
                notificationType = notification.type//NotificationType.FOLLOW.type
            )
    }
}