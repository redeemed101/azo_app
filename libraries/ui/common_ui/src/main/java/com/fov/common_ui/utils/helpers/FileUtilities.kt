package com.fov.common_ui.utils.helpers

import android.content.Context
import android.graphics.Bitmap
import android.system.Os
import android.system.StructStat
import androidx.lifecycle.LiveData
import androidx.palette.graphics.Palette
import androidx.work.*
import com.fov.common_ui.utils.constants.Constants
import com.fov.common_ui.workers.DownloadSongWorker
import java.io.BufferedInputStream
import java.io.File
import java.io.FileOutputStream
import java.net.URL


class FileUtilities(

) {

    companion object {
        fun getFileExtension(path : String) : String{
            return path.substring(path.lastIndexOf("."));
        }
        fun getLastAccessTime(path : String) : Long {
            try {
                val stat: StructStat = Os.stat(path)
                return stat.st_atime

            } catch (e: Exception) {
               return 0L
            }
        }
        fun createPaletteAsync(bitmap: Bitmap,callback : (Palette?) -> Unit) {
            Palette.from(bitmap).generate { palette ->
                // Use generated instance
                callback(palette)
            }
        }
        fun downloadNetworkImage(url : String, destinationPath : String, imageName : String) : String{
            var file = File(destinationPath,"$imageName.jpg")

            var url = URL(url)
            var connection = url.openConnection()
            connection.connect()

            var fileLength = connection.contentLength

            var input = BufferedInputStream(url.openStream())
            var output = FileOutputStream(file.absolutePath)
            val data = ByteArray(1024)
            var total = 0
            var count : Int
            while (input.read(data).also { count = it } != -1 ) {
                total += count
                var p = (total.toDouble() / fileLength) * 100
                output.write(data, 0, count)
            }
            output.flush()
            output.close()
            input.close()
            return file.absolutePath
        }
        fun downloadSongFile(
                             url : String,
                             imageUrl : String,
                             encryptionKey: String = "",
                             destinationFile :  String,
                             fileDetails : String,
                             applicationContext: Context
        ) : LiveData<WorkInfo> {

            val workManager = WorkManager.getInstance(applicationContext)
            workManager.cancelAllWork()
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                //.setRequiresStorageNotLow(true)
                .build()
            val inputData = Data.Builder()
                .putString(Constants.DOWNLOAD_URL,url)
                .putString(Constants.DOWNLOAD_IMAGE_URL,imageUrl)
                .putString(Constants.DOWNLOAD_DETAILS,fileDetails)
                .putString(Constants.DOWNLOAD_FILE_ENCRYPTION_KEY,encryptionKey)
                .putString(Constants.DOWNLOAD_DESTINATION_FILE,destinationFile)
                .build()
            val task = OneTimeWorkRequest
                .Builder(DownloadSongWorker::class.java)
                .setInputData(inputData)
                .setConstraints(constraints).build()
            workManager.enqueue(task)

            return workManager.getWorkInfoByIdLiveData(task.id)
        }
    }
}