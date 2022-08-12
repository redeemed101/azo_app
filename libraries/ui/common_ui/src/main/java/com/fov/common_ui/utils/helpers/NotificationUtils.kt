package com.fov.common_ui.utils.helpers

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import com.fov.common_ui.R

private val NOTIFICATION_ID = 0
private val REQUEST_CODE = 0
private val FLAGS = 0

fun NotificationManager.cancelNotifications() {
    cancelAll()
}
fun NotificationManager.updateDownloadProgress(
    title:String,
    messageBody: String,
    progressMax:Int,
    progressCurrent:Int,
    builder  :  NotificationCompat.Builder,
    notificationId: Int
){
    builder.setContentTitle(title)
        .setContentText(messageBody)
        .setProgress(progressMax, progressCurrent, false)
    notify(notificationId, builder.build())
}
fun NotificationManager.sendDownloadNotification(title:String,
                                                 messageBody: String,
                                                 progressMax:Int = 100,
                                                 progressCurrent:Int = 0,
                                                 applicationContext: Context,
                                                 notificationId : Int) : NotificationCompat.Builder{

    val builder = NotificationCompat.Builder(applicationContext,
        applicationContext.getString(R.string.fidarr_notification_channel_id)).apply {
        setContentTitle(title)
        setSilent(true)

        setContentText(messageBody)
        setSmallIcon(R.drawable.logo_red)
        priority = NotificationCompat.PRIORITY_LOW
    }

    apply {
        // Issue the initial notification with zero progress
        builder.setProgress(progressMax,progressCurrent, false)
        notify(notificationId, builder.build())

    }
    return  builder;
}
fun NotificationManager.sendNotification(title:String, messageBody: String, applicationContext: Context) : NotificationCompat.Builder {
    // Create the content intent for the notification, which launches
    // this activity
    //val contentIntent = Intent(applicationContext, MainActivity::class.java)

    // Step 1.12 create PendingIntent
    /*val contentPendingIntent = PendingIntent.getActivity(
        applicationContext,
        NOTIFICATION_ID,
        contentIntent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )*/

    // Build the notification
    val builder = NotificationCompat.Builder(
        applicationContext,
        applicationContext.getString(R.string.fidarr_notification_channel_id)
    )

        .setSmallIcon(R.drawable.logo_red)
        .setContentTitle(title)
        .setContentText(messageBody)

        // Step 1.13 set content intent
        //.setContentIntent(contentPendingIntent)
        .setAutoCancel(true)

    notify(NOTIFICATION_ID, builder.build())

    return builder;
}

class NotificationUtils{
    companion object {
        fun createNotificationChannel(applicationContext: Context) {
            // Create the NotificationChannel, but only on API 26+ because
            // the NotificationChannel class is new and not in the support library
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val id = applicationContext.getString(R.string.fidarr_notification_channel_id)
                val name = applicationContext.getString(R.string.fidarr_notification_channel_name)
                val descriptionText =
                    applicationContext.getString(R.string.fidarr_channel_description)
                val importance = NotificationManager.IMPORTANCE_DEFAULT
                val channel = NotificationChannel(id, name, importance).apply {
                    description = descriptionText
                }
                channel.enableVibration(true)
                // Register the channel with the system
                val notificationManager: NotificationManager =
                    applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.createNotificationChannel(channel)
            }
        }
    }
}