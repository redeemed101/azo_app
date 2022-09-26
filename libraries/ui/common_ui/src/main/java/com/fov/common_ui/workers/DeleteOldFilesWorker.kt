package com.fov.common_ui.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.fov.common_ui.utils.constants.Constants
import com.fov.common_ui.utils.helpers.FileUtilities
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.time.Duration
import java.time.Instant
import java.time.LocalDateTime
import java.util.*

class DeleteOldFilesWorker constructor(
    context: Context,
    workerParams: WorkerParameters,
) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result {

        val fileExtension = inputData.getString(Constants.FILE_EXTENSION)
        val directoryPath = inputData.getString(Constants.DIRECTORY)
        val howOldString = inputData.getString(Constants.HOW_OLD_DAYS)
        val howOld = howOldString!!.toLongOrNull()
        val directory = File(directoryPath!!)
        return try {
            // Do something
            directory.walk().forEach {
                if(it.isDirectory) {
                    val files = it.listFiles { file ->
                        file.name.contains(fileExtension!!) && file.length() > 0
                    }
                    Log.d("DELETE_OLD","${files.size}")
                    files.forEach {  f ->
                            Log.d("DELETE_OLD__","${f.name}")
                            if(f.endsWith(fileExtension!!) && Files.exists(Paths.get(f.absolutePath)) && f.length() > 0){

                                val lastAccessTime = FileUtilities.getLastAccessTime(f.absolutePath)
                                val last = Date(lastAccessTime)
                                val curr = Date()
                                if(Duration.between(curr.toInstant(),last.toInstant()).toDays() > howOld!!){
                                    Files.delete(Paths.get(f.absolutePath))
                                }

                            }
                    }

                }
                else{
                    Log.d("DELETE_OLD","${it.name}")
                    if(it.endsWith(fileExtension!!) && Files.exists(Paths.get(it.absolutePath)) && it.length() > 0){

                        val lastAccessTime = FileUtilities.getLastAccessTime(it.absolutePath)
                        val last = Date(lastAccessTime)
                        val curr = Date()
                        if(Duration.between(curr.toInstant(),last.toInstant()).toDays() > howOld!!){
                            Files.delete(Paths.get(it.absolutePath))
                        }

                    }
                }

            }

            Result.success()
        } catch (error: Throwable) {
            Result.failure()
        }
    }

}