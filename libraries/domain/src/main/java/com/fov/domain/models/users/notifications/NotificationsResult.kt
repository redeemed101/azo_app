package com.fov.domain.models.users.notifications
import com.fov.domain.models.users.notifications.Notification
import kotlinx.serialization.Serializable


@Serializable
data class NotificationsResult(
    val notifications : List<Notification> = listOf()
)
