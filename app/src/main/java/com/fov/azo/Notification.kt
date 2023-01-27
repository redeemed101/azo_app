package com.fov.azo

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Handler
import android.os.Looper
import android.transition.Transition
import android.util.Log
import androidx.core.app.NotificationCompat
import com.bumptech.glide.Glide
import com.fov.common_ui.R
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso
import com.squareup.picasso.Picasso.LoadedFrom
import java.lang.Exception


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

            .setSmallIcon(R.drawable.fov_logo)
            .setContentTitle(title)
            .setContentText(messageBody)
            .setContentIntent(contentPendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
        notify(NOTIFICATION_ID, builder.build())
    }


    fun NotificationManager.sendNotificationWithImageUrl(messageBody: String,title : String, imageUrl: String, applicationContext: Context) {


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
        Log.d("ImageLoadUrl","Reached here ${imageUrl}")
            Handler(Looper.getMainLooper()).post {
                Picasso.get()
                    .load(imageUrl)
                    .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                    .into(object : com.squareup.picasso.Target {
                        override fun onBitmapLoaded(bitmap: Bitmap?, from: LoadedFrom?) {
                            val builder = NotificationCompat.Builder(
                                applicationContext,
                                applicationContext.getString(R.string.fov_notification_channel_id)
                            )

                                .setSmallIcon(R.drawable.fov_logo)
                                .setContentTitle(title)
                                .setContentText(messageBody)
                                .setContentIntent(contentPendingIntent)
                                .setPriority(NotificationCompat.PRIORITY_HIGH)
                                .setAutoCancel(true)
                            //builder.setLargeIcon(bitmap) //largeIcon
                            builder.setStyle(
                                NotificationCompat.BigPictureStyle().bigPicture(bitmap)
                            )
                            notify(NOTIFICATION_ID, builder.build())
                            Log.d("ImageLoad","Reached here")
                        }

                        override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
                            Log.d("ImageLoadFailed","Reached here ${e!!.message}")
                        }

                        override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
                            Log.d("ImageLoadPrepare","Reached here")
                        }

                    })
            }



    }

    fun NotificationManager.cancelNotifications() {
        cancelAll()
    }
}