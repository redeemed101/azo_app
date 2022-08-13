package com.fov.core.utils

import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Build
import androidx.annotation.RequiresApi
import com.fov.core.di.Preferences
import kotlinx.coroutines.flow.collect
import org.ocpsoft.prettytime.PrettyTime
import java.time.LocalDate
import java.time.ZoneId
import java.util.*


class Utilities(
    private val preferences: Preferences
) {

    suspend fun getToken(): String {
        var token = ""
        preferences.accessToken.collect { tok ->
            if (tok != null) {
                token = tok
            }
        }
        return token
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun getPrettyTime(date : String) : String {
        val p = PrettyTime()
        val defaultZoneId: ZoneId = ZoneId.systemDefault()
        val d = LocalDate.parse(date)
        return p.format(Date.from(d.atStartOfDay(defaultZoneId).toInstant()))

    }
}

object Utils{
    @RequiresApi(Build.VERSION_CODES.O)
    fun getPrettyTime(date: String) : String {
        val p = PrettyTime()
        val d = LocalDate.parse(date)
        val defaultZoneId: ZoneId = ZoneId.systemDefault()
        return p.format(Date.from(d.atStartOfDay(defaultZoneId).toInstant()))

    }
    private fun createSingleImageFromMultipleImages(
        firstImage: Bitmap,
        secondImage: Bitmap
    ): Bitmap? {
        val result: Bitmap = Bitmap.createBitmap(
            firstImage.width + secondImage.width,
            firstImage.height,
            firstImage.config
        )
        val canvas = Canvas(result)
        canvas.drawBitmap(firstImage, 0f, 0f, null)
        canvas.drawBitmap(secondImage, firstImage.width.toFloat(), 0f, null)
        return result
    }
}