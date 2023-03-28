package com.fov.azo

import android.app.NotificationManager
import android.util.Log
import androidx.core.content.ContextCompat
import com.fov.authentication.viewModels.UsersViewModel
import com.fov.azo.Notification.sendNotification
import com.fov.azo.Notification.sendNotificationWithImageUrl
import com.fov.common_ui.utils.helpers.UrlPathHelper
import com.fov.domain.interactors.authentication.Authenticate
import com.fov.domain.repositories.authentication.AuthenticationRepository
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import java.lang.Exception
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import javax.inject.Inject
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FcmService : FirebaseMessagingService() {
    companion object {
        private const val TAG = "FcmService"
    }
    @Inject
    lateinit var usersViewModel: Authenticate

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
            if(it.imageUrl != null){
                Log.d(TAG, " ${it.imageUrl.toString()}")
                val imgUrl = it.imageUrl.toString()
                Log.d(TAG, " The Url is ${imgUrl}")
                if (imgUrl != null) {
                    sendNotificationWithImage(it.body!!, it.title!!,imgUrl )
                }
            }
            else {
                sendNotification(it.body!!, it.title!!)
            }
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
    private fun sendNotificationWithImage(messageBody: String,title: String, imageUrl: String){
        val notificationManager = ContextCompat.getSystemService(applicationContext, NotificationManager::class.java) as NotificationManager
        notificationManager.sendNotificationWithImageUrl(messageBody,title, imageUrl, applicationContext)
    }
    private fun sendRegistrationToServer(token: String){
          Log.d("token",token)

            GlobalScope.launch {
                try {
                usersViewModel.sendDeviceToken(token)
                }
                catch(ex : Exception){
                    Log.e("token", ex.message.toString())
                }
            }

    }
}