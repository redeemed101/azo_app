package com.fov.common_ui.workers

import android.app.NotificationManager
import android.content.Context
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.work.*
import com.fov.common_ui.R
import com.fov.common_ui.utils.constants.Constants
import com.fov.common_ui.utils.helpers.FileUtilities
import com.fov.common_ui.utils.helpers.sendDownloadNotification
import com.fov.common_ui.utils.helpers.updateDownloadProgress
import com.fov.core.security.fileEncryption.FileEncryption
import java.io.BufferedInputStream
import java.io.File
import java.io.FileOutputStream
import java.net.URL
import javax.inject.Inject
import kotlin.math.absoluteValue
import kotlin.random.Random

class DownloadSongWorker (context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {
    val PROGRESS  : String = "Progress"

    var cancelDownload = false


    @Inject
    lateinit var fileEncryption : FileEncryption

    lateinit var builder: NotificationCompat.Builder

    lateinit var notificationManager: NotificationManager

    var  NOTIFICATION_ID : Int = 0

    override fun doWork(): Result {
        NOTIFICATION_ID = Random(System.nanoTime()).nextInt(100)
        cancelDownload = false
        val url = inputData.getString(Constants.DOWNLOAD_URL)
        val imageUrl = inputData.getString(Constants.DOWNLOAD_IMAGE_URL)
        val destinationFilePath = inputData.getString(Constants.DOWNLOAD_DESTINATION_FILE)
        val secretKey = inputData.getString(Constants.DOWNLOAD_FILE_ENCRYPTION_KEY)
        val details = inputData.getString(Constants.DOWNLOAD_DETAILS)
        val firstUpdate = workDataOf(PROGRESS to 0)
        val lastUpdate = workDataOf(PROGRESS to 100)
        //first notification
        notificationManager = ContextCompat.getSystemService(
            applicationContext,
            NotificationManager::class.java
        ) as NotificationManager
        builder =  notificationManager.sendDownloadNotification(
            "File  Download",
            "Downloading $details in progress",
            100,
            0,
            applicationContext,
            NOTIFICATION_ID
        )
        return try {
            var count: Int
            val intent = WorkManager.getInstance(applicationContext)
                .createCancelPendingIntent(id)


            builder.addAction(android.R.drawable.ic_delete, "Cancel", intent);

            var file = File(destinationFilePath)
            if(!file.exists()){
                file.parentFile.mkdirs()
            }
            var url = URL(url)
            var connection = url.openConnection()
            connection.connect()

            var fileLength = connection.contentLength

            var input = BufferedInputStream(url.openStream())
            var output = FileOutputStream(destinationFilePath)
            val data = ByteArray(1024)
            Log.d("_progress_value","size: $fileLength")
            var total = 0
            setProgress(firstUpdate,notificationManager,builder)

            while (input.read(data).also { count = it } != -1 && !cancelDownload) {


                Log.d("_count_progress", "$count $fileLength ${((count.toDouble()/fileLength)*100).absoluteValue}")

                total += count
                var p = (total.toDouble() / fileLength) * 100
                setProgress(workDataOf(PROGRESS to p.toInt()), notificationManager, builder)
                output.write(data, 0, count)
            }


            output.flush()
            output.close()
            input.close()
            if(!cancelDownload) {
                finish(notificationManager, builder)
            }


            //encrypt file
            //delete file
            val basePath = "${com.fov.common_ui.utils.helpers.Utilities
                .getOutputDirectory(
                    applicationContext
                ).absolutePath}"
            val path = "$basePath/$details${FileUtilities.getFileExtension(destinationFilePath!!)}";
            val encryptedFile = fileEncryption.encryptFile(destinationFilePath!!,path,secretKey!!)
            file.delete()
            if(encryptedFile != null) {
                val imagePath = FileUtilities.downloadNetworkImage(imageUrl!!,basePath,details!!)

                val outputData = workDataOf("FILEPATH" to arrayOf(encryptedFile!!.absolutePath,imagePath))

                Result.success(outputData)
            }
            Result.failure(workDataOf("" to ""))
        }
        catch (throwable: Throwable) {
            Log.e("Download_failure", "Error downloading file")

            builder.setContentTitle("Download Failed")
                .setContentText("Failed to download $details ${throwable.message}")
                .setSmallIcon(R.drawable.avatar)
                .clearActions()
                .setProgress(0,0,false)

            notificationManager.notify(NOTIFICATION_ID,
                builder.build())
            Result.failure()
        }
    }



    private fun finish(notificationManager: NotificationManager,
                       builder: NotificationCompat.Builder) {

        builder.setContentTitle("Download complete")
            .setContentText("The File was successfully  downloaded")
            .setSmallIcon(R.drawable.avatar)
            .setProgress(0,0,false)
            .clearActions()

        notificationManager.notify(NOTIFICATION_ID,
            builder.build())
    }

    private fun setProgress(
        workDataOf: Data,
        notificationManager: NotificationManager,
        builder: NotificationCompat.Builder
    ) {
        val  d = workDataOf.keyValueMap
        val data  = d[PROGRESS];
        notificationManager.updateDownloadProgress(
            "Download in Progress",
            "Download at ${data}% ",
            100,
            data.toString().toInt(),
            builder,
            NOTIFICATION_ID
        )

    }

    override fun onStopped() {
        notificationManager.cancel(NOTIFICATION_ID)
        cancelDownload = true
        builder.setContentTitle("Download Cancelled")
            .setContentText("")
            .setSmallIcon(R.drawable.avatar)
            .clearActions()
            .setProgress(0,0,false)
        //.setAutoCancel(true)
        notificationManager.notify(NOTIFICATION_ID,
            builder.build())

        super.onStopped()
    }
}