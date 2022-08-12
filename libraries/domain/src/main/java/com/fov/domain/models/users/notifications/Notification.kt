package com.fov.domain.models.users.notifications

import com.fov.domain.models.authentication.users.UserDTO
import kotlinx.serialization.Serializable


@Serializable
data class Notification(
    val id: Long,
    val dateCreated: String,
    val type: String,
    val subject: String,
    val description: String,
    val read: Boolean,
    val notifier: UserDTO,
    val notified: UserDTO
)
