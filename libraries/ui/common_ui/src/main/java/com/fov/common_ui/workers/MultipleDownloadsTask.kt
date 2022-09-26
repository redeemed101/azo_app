package com.fov.common_ui.workers

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedInputStream
import java.io.File
import java.io.FileOutputStream
import java.net.URL

class MultipleDownloadsTask(
    val downloadPaths: List<MultipleDownload>,
    private val listener: MultipleDownloadsListener,
) {
    var cancelDownload = false
    suspend fun downloadFile() : Boolean{
        return try {
            withContext(Dispatchers.IO){
                val total = downloadPaths.size
                Log.d("ALBUM_DOWNLOAD", "$total")
                var totalDownloaded = 0
                for (multipleDownload in downloadPaths) {
                    var progress = 0.00
                    setProgress(progress)
                    var count: Int
                    var file = File(multipleDownload.destinationPath)
                    if (!file.exists()) {
                        file.parentFile.mkdirs()
                    }
                    var url = URL(multipleDownload.url)
                    var connection = url.openConnection()

                    connection.connect()
                    var fileLength = connection.contentLength
                    var input = BufferedInputStream(url.openStream())
                    var output = FileOutputStream(multipleDownload.destinationPath)
                    val data = ByteArray(1024)
                    var totalBytes = 0


                    while (input.read(data).also { count = it } != -1 && !cancelDownload) {
                        totalBytes += count
                        progress = (totalBytes.toDouble() / fileLength) //* 100
                        setProgress(progress)
                        output.write(data, 0, count)
                    }

                    output.flush()
                    output.close()
                    input.close()
                    if (!cancelDownload) {
                        totalDownloaded++
                        Log.d("ALBUM_DOWNLOAD__", "$totalDownloaded of $total")
                        val p = totalDownloaded.toFloat()/total.toFloat()
                        setTotalProgress(p.toDouble())
                        Log.d("ALBUM_DOWNLOAD_", "$p")
                        listener.onOneDownloadComplete(multipleDownload.destinationPath)
                        if(totalDownloaded == total){
                            listener.onDownloadComplete(true)
                        }

                    }
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
    private fun setTotalProgress(progress: Double){
        listener.downloadTotalProgress(progress)
    }
    private fun finish(){
        listener.onDownloadComplete(true)
    }
}

interface MultipleDownloadsListener {
    fun onDownloadComplete(download: Boolean)
    fun onOneDownloadComplete(downloadedPath: String)
    fun downloadProgress(status: Double)
    fun downloadTotalProgress(status: Double)
    fun errorOccurred(throwable: Throwable)
}
data class MultipleDownload(
    val identifier: String,
    val url : String,
    val destinationPath: String
)