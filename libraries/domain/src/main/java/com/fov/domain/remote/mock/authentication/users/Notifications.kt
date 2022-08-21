package com.fov.domain.remote.mock.authentication.users

import com.fov.domain.database.models.User
import com.fov.domain.models.authentication.users.UserDTO
import com.fov.domain.models.users.notifications.Notification
import com.fov.domain.models.users.notifications.NotificationsResult
import com.fov.domain.utils.constants.NotificationType
import com.google.gson.Gson

object UserNumNotificationsMockResponse{
    operator fun invoke():String{
        val response = 30
        return Gson().toJson(response)
    }
}
object UserNotificationsMockResponse {
    operator fun invoke(): String {
       val response  = NotificationsResult(
           notifications = listOf(
               Notification(
                   id = 1234,
                   dateCreated = "2021-08-08",
                   type = NotificationType.FOLLOW.type,
                   subject = "Following",
                   description = "Lewis started following you",
                   read = false,
                   notifier = UserDTO(
                       fullName = "Lewis Msasa",
                       email = "lmsasa@gmail.com",
                       privateKey = "1234",
                       publicKey = "1234",
                       id = "12345",
                       emailConfirmed = false,
                       phoneNumber = "",
                   ),
                   notified = UserDTO(
                       fullName = "Lewis Msasa Jnr",
                       email = "lmsasajnr@gmail.com",
                       privateKey = "1234",
                       publicKey = "1234",
                       id = "12345",
                       emailConfirmed = false,
                       phoneNumber = "",
                   )

               )
           )
       )

        return Gson().toJson(response)
    }
}