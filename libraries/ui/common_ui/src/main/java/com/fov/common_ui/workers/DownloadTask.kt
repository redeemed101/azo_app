package com.fov.common_ui.workers

import android.R
import android.annotation.SuppressLint
import android.app.NotificationManager
import android.content.Context
import android.os.AsyncTask
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.work.Data
import androidx.work.ListenableWorker
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.fov.common_ui.utils.helpers.FileUtilities
import com.fov.common_ui.utils.helpers.NotificationAction
import com.fov.common_ui.utils.helpers.sendDownloadNotification
import com.fov.common_ui.utils.helpers.updateDownloadProgress
import com.fov.core.security.fileEncryption.FileEncryption
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedInputStream
import java.io.File
import java.io.FileOutputStream
import java.net.URL
import kotlin.random.Random

class DownloadTask(
    val context: Context,
    val url: String,
    private val destinationPath : String,
    private val listener: DownloadListener) {

    var cancelDownload = false
    suspend fun downloadFile() : Boolean{
        return try {
           withContext(Dispatchers.IO){
            var progress = 0.00
            setProgress(progress)
            var count: Int
            var file = File(destinationPath)
            if (!file.exists()) {
                file.parentFile.mkdirs()
            }
            Log.d("DOWNLOAD_URL", url)
            var url = URL(url)
            Log.d("_progress_value1", "size:")
            var connection = url.openConnection()
            Log.d("_progress_value2", "size:")

            connection.connect()
            Log.d("_progress_value3", "size:")
            var fileLength = connection.contentLength
            var input = BufferedInputStream(url.openStream())
            var output = FileOutputStream(destinationPath)
            val data = ByteArray(1024)
            Log.d("_progress_value", "size: $fileLength")
            var total = 0


            while (input.read(data).also { count = it } != -1 && !cancelDownload) {
                total += count
                progress = (total.toDouble() / fileLength) //* 100
                Log.d("DOWNLOAD_PROGRESS", "size: $fileLength")
                setProgress(progress)
                output.write(data, 0, count)
            }
            output.flush()
            output.close()
            input.close()
            Log.d("END_DOWNLOAD", "${cancelDownload}")
            if (!cancelDownload) {
                Log.d("END_DOWNLOAD", "notification")

                finish()
            }

          }
          return true
        }
        catch (throwable: Throwable) {
            Log.e("Download_failure", "Error downloading file ${throwable.message}")
            throwable.printStackTrace()
            listener.errorOccurred(throwable)
            listener.onDownloadComplete(false)
            return false
        }
    }
    private fun setProgress(
        progress : Double
    ) {
        listener.downloadProgress(progress)

    }
    private fun finish(){
        listener.onDownloadComplete(true)
    }
 }

    interface DownloadListener {
        fun onDownloadComplete(download: Boolean)
        fun downloadProgress(status: Double)
        fun errorOccurred(throwable: Throwable)
    }