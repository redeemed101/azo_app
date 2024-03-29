package com.fov.common_ui.utils.helpers

import android.content.Context
import androidx.compose.ui.graphics.Color
import com.fov.common_ui.R
import java.io.File
import kotlin.random.Random

object Utilities {

    fun randomColor() : Color {


        return Color(red = Random.nextInt(256),
            green = Random.nextInt(256),
            blue = Random.nextInt(256),
            //alpha = Random.nextInt(256)
        )
    }
    fun yearsRange(lowerBound:Int,upperBound:Int): List<Int>{
        var l = mutableListOf<Int>()
        var currYear = upperBound
        while (currYear >= lowerBound){

            l.add(currYear)
            currYear -= 1
        }
        return l
    }
    fun getDataDirectory(context: Context) : File{
        val appContext = context.applicationContext
        val dataDir = context.dataDir.let{
            File(it,appContext.resources.getString(R.string.app_name)).apply {
                mkdirs()
            }
        }
        return if(dataDir != null && dataDir.exists())
            dataDir else appContext.dataDir

    }
    fun getCacheDirectory(context: Context) : File{
        val appContext = context.applicationContext
        val cacheDir = context.externalCacheDir.let{
            File(it,appContext.resources.getString(R.string.app_name)).apply {
                mkdirs()
            }
        }
        return if(cacheDir != null && cacheDir.exists())
              cacheDir else appContext.cacheDir

    }
    fun getOutputDirectory(context: Context) : File{
        val appContext = context.applicationContext
        val mediaDir = context.getExternalFilesDir(null).let {
            File(it,appContext.resources.getString(R.string.app_name)).apply {
                mkdirs()
            }
        }

        /*context.externalMediaDirs.firstOrNull()?.let{
            File(it,appContext.resources.getString(R.string.app_name)).apply {
                mkdirs()
            }
        }*/
        return if(mediaDir != null && mediaDir.exists())
            mediaDir else appContext.filesDir

    }
}