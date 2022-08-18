package com.fov.main.utils.extensions

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.fov.common_ui.R
import com.fov.main.MainActivity

object Notification {
    // Notification ID.
    private const val NOTIFICATION_ID = 0

    fun NotificationManager.sendNotification(messageBody: String,title : String, applicationContext: Context) {


        // TODO: Step 1.11 create intent
        val contentIntent = Intent(applicationContext, MainActivity::class.java)

        // TODO: Step 1.12 create PendingIntent
        val contentPendingIntent = PendingIntent.getActivity(
            applicationContext,
            NOTIFICATION_ID,
            contentIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        // TODO: You can add style here


        // TODO: Step 1.2 get an instance of NotificationCompat.Builder

        // Build the notification
        val builder = NotificationCompat.Builder(
            applicationContext,
            applicationContext.getString(R.string.fov_notification_channel_id)
        )

            .setSmallIcon(R.drawable.ic_message_circle)
            .setContentTitle(title)
            .setContentText(messageBody)
            .setContentIntent(contentPendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
        notify(NOTIFICATION_ID, builder.build())
    }

    fun NotificationManager.cancelNotifications() {
        cancelAll()
    }
}