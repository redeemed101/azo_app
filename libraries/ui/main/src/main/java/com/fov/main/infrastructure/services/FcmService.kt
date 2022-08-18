package com.fov.main.infrastructure.services

import android.app.NotificationManager
import android.util.Log
import androidx.core.content.ContextCompat
import com.fov.main.utils.extensions.Notification.sendNotification
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import java.lang.Exception


class FcmService : FirebaseMessagingService() {
    companion object {
        private const val TAG = "FcmService"
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        // Check if message contains a data payload, you can get the payload here and add as an intent to your activity
        remoteMessage.data.let {
            Log.d(TAG, "Message data payload: " + remoteMessage.data)
            //get the data
        }

        // Check if message contains a notification payload, send notification
        remoteMessage.notification?.let {
            it.title
            Log.d(TAG, "Message Notification Body: ${it.body}")
            sendNotification(it.body!!,it.title!!)
        }
    }

    override fun onDeletedMessages() {
        super.onDeletedMessages()
    }

    override fun onMessageSent(p0: String) {
        super.onMessageSent(p0)
    }

    override fun onSendError(p0: String, p1: Exception) {
        super.onSendError(p0, p1)
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        sendRegistrationToServer(token)
    }

    private fun sendNotification(messageBody: String,title: String){
        val notificationManager = ContextCompat.getSystemService(applicationContext, NotificationManager::class.java) as NotificationManager
        notificationManager.sendNotification(messageBody,title, applicationContext)
    }
    private fun sendRegistrationToServer(token: String){

    }
}